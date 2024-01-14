package com.example.oria.backend.server

import android.speech.SpeechRecognizer.ERROR_SERVER
import com.example.oria.backend.data.storage.point.PointDetails
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.data.storage.trip.CurrentTrip
import com.example.oria.backend.data.storage.trip.TripDetails
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.CONNECTION_TIMEOUT
import com.example.oria.ui.theme.REQUEST_TIMEOUT
import com.example.oria.ui.theme.URL_BASE
import com.example.oria.viewModel.auth.RegisterState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.*

/**
 * Class for client http
 *
 */
class OriaClient {

    companion object {

        @Volatile
        private var instance: OriaClient? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: OriaClient().also { instance = it }
        }
    }

    val client = HttpClient(CIO) {

        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT
            connectTimeoutMillis = CONNECTION_TIMEOUT
        }
        install(ResponseObserver) {
            onResponse { response ->
                DEBUG(TagDebug.SERVER, "${response.status.value}")
            }
        }
        install(ContentNegotiation) {
            json()
        }

    }
    lateinit var tripsRepository: TripRepository
    lateinit var pointRepository: PointRepository

    /**
     *  Functions for authentication
     */

    /**
     * Function to send login request to the server
     *
     * @param username
     * @param password
     * @return
     */
    suspend fun login(username: String, password: String): LoginResponse {
        try {
            val response = client.post("$URL_BASE/auth/signin") {
                setBody(MultiPartFormDataContent(formData {
                    append("username", username)
                    append("password", password)
                }))
            }
            val loginResponse: LoginResponse = response.body()
            DEBUG(TagDebug.SERVER, "Response Status : ${response.status}")
            if (response.status.value in 500..599) {
                 return LoginResponse(
                     ERROR_CODE=loginResponse.ERROR_CODE,
                     TRIPS = listOf()
                 )
            }
            return loginResponse
        } catch (e: Exception) {
            ERROR(TagDebug.SERVER, e.message.toString())
        }
        return return LoginResponse(
            ERROR_CODE= ERROR_SERVER,
            TRIPS = listOf()
        )
    }

    /**
     * Function to send register request to the server
     *
     * @param registerState
     * @return
     */
    suspend fun register(registerState: RegisterState): RegisterResponse {
        val response = client.post("$URL_BASE/auth/signup") {
            setBody(MultiPartFormDataContent(formData {
                append("username", registerState.username)
                append("firstname", registerState.firstname)
                append("lastname", registerState.lastname)
                append("email", registerState.email)
                append("password", registerState.password)
                append("confirmpwd", registerState.confirmpwd)
            }))
        }
        return when (response.status.value) {
            in 500..599 -> {
                RegisterResponse(
                    ERROR_CODE = ERROR_SERVER, TOKEN = " "
                )
            }

            else -> response.body()
        }
    }

    /**
     *  Functions for trip management
     */

    /**
     * Function to create a trip on the server
     *
     * @param tripDetails
     * @param username
     * @return the trip id created on the server
     */
    suspend fun createTrip(tripDetails: TripDetails, username: String): CreateTripResponse {
        val response = client.post("$URL_BASE/trip/create_trip") {
            setBody(MultiPartFormDataContent(formData {
                append("name", tripDetails.name)
                append("description", tripDetails.description)
                append("place", tripDetails.location)
                append("username", username)
            }))
        }
        return when (response.status.value) {
            in 500..599 -> {
                CreateTripResponse(
                    ERROR_CODE = ERROR_SERVER, TRIP_ID = 0
                )
            }

            else -> response.body()
        }
    }

    /**
     * Function to import trip from the server
     *
     * @param trip_Id
     * @param username
     * @return trip details
     */
    suspend fun importTrip(trip_Id: Int, username: String): ImportTripResponse {

        val response = client.post("$URL_BASE/trip/import_trip") {
            setBody(MultiPartFormDataContent(formData {
                append("trip_id", trip_Id)
                append("username", username)
            }))
        }
        return when (response.status.value) {
            in 500..599 -> {
                ImportTripResponse(
                    ERROR_CODE = ERROR_SERVER, TRIP = TripDetails(), POINTS = listOf()
                )
            }

            else -> response.body()
        }
    }

    /**
     *  Functions for point management
     */
    suspend fun createPoint(pointDetails: PointDetails, username: String, trip_id: Int):
            CreatePointResponse{
        val response = client.post("$URL_BASE/point/create_point"){
            setBody(MultiPartFormDataContent(formData {
                append("name", pointDetails.name)
                append("description", pointDetails.description)
                append("location", pointDetails.location)
                append("trip_id", trip_id)
                append("image", pointDetails.image)
            }))
        }
        return when (response.status.value) {
            in 500..599 -> {
                CreatePointResponse(
                    ERROR_CODE = ERROR_SERVER,
                    POINT_ID = 0
                )
            }

            else -> response.body()
        }
    }

    suspend fun importPoint(point_id: Int): ImportPointResponse{
        val response = client.post("$URL_BASE/point/import_point"){
            setBody(MultiPartFormDataContent(formData{
                append("point_id", point_id)
            }))
        }
        return when (response.status.value) {
            in 500..599 -> {
                ImportPointResponse(
                    ERROR_CODE = ERROR_SERVER,
                    POINT = PointDetails()
                )
            }

            else -> response.body()
        }
    }


    suspend fun callImportTrip(trip_id: Int, username: String){
        val response: ImportTripResponse =
            OriaClient.getInstance().importTrip(
                trip_id,
                username
            )
        val imported_trip: TripDetails = response.TRIP

        DEBUG(TagDebug.CREATE_TRIP, imported_trip.toString())
        tripsRepository.insertTrip(imported_trip.toTrip())
        CurrentTrip.getInstance().updateCurrentTripCode(imported_trip.id)

        importAllPoints(response.POINTS)

    }

    private suspend fun importAllPoints(points: List<Int>){
        for(point in points){
            val point_details: ImportPointResponse = OriaClient.getInstance().importPoint(point)
            DEBUG(TagDebug.CREATE_POINT,"Import point: ${point_details.POINT}")
            pointRepository.insertPoint(point_details.POINT.toPoint())
        }
    }

    suspend fun deletePoint(pointDetails: PointDetails): DeletePointResponse{
        val response = client.post("$URL_BASE/point/delete_point"){
            setBody(MultiPartFormDataContent(formData{
                append("id",pointDetails.id)
            }))
        }
        return when (response.status.value) {
            in 500..599 -> {
                DeletePointResponse(
                    ERROR_CODE = ERROR_SERVER
                )
            }

            else -> response.body()
        }
    }
}
