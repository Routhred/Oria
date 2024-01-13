package com.example.oria.backend.server

import android.speech.SpeechRecognizer.ERROR_SERVER
import com.example.oria.backend.data.storage.point.PointDetails
import com.example.oria.backend.data.storage.trip.TripDetails
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.CONNECTION_TIMEOUT
import com.example.oria.ui.theme.ERROR_REQUEST
import com.example.oria.ui.theme.NO_ERROR
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
    suspend fun login(username: String, password: String): Int {
        try {
            val response = client.post("$URL_BASE/auth/signin") {
                setBody(MultiPartFormDataContent(formData {
                    append("username", username)
                    append("password", password)
                }))
            }
            val loginResponse: LoginResponse = response.body()
            DEBUG(TagDebug.SERVER, "Response Status : ${response.status}")
            DEBUG(TagDebug.SERVER, "Response Body : ${loginResponse.ERROR_CODE}")
            when (response.status.value) {
                in 200..299 -> return NO_ERROR
                in 500..599 -> return ERROR_SERVER
                else -> return loginResponse.ERROR_CODE
            }
        } catch (e: Exception) {
            ERROR(TagDebug.SERVER, e.message.toString())
        }
        return ERROR_REQUEST
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
}
