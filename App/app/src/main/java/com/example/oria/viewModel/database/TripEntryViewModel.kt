package com.example.oria.viewModel.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.data.storage.trip.CurrentTrip
import com.example.oria.backend.data.storage.trip.TripDetails
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.backend.server.CreateTripResponse
import com.example.oria.backend.server.OriaClient
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.ERROR_SERVER
import com.example.oria.ui.theme.EXIT_ERROR
import com.example.oria.ui.theme.NO_ERROR

/**
 * View Model for the new trip view
 *
 * @property tripsRepository
 */
class TripEntryViewModel(
    private val tripsRepository: TripRepository,
    preferencesManager: PreferencesManager
) : ViewModel() {

    private val preferencesManager = preferencesManager

    var tripUiState by mutableStateOf(TripUiState())
        private set

    /**
     * Update current ui state with new field
     *
     * @param tripDetails tripDetails with new field to update current ui state
     */
    fun updateUiState(tripDetails: TripDetails) {
        tripUiState = TripUiState(
            tripDetails = tripDetails,
            isEntryValid = validateInput(tripDetails)
        )
    }

    /**
     * Save current Ui State item to the database
     *
     * @return Id of the trip create in the database
     */
    suspend fun saveItem(): Int {

        val response: CreateTripResponse =
            OriaClient.getInstance().createTrip(
                tripUiState.tripDetails,
                preferencesManager.getData(PreferencesKey.USERNAME, "")
            )
        val error_code = response.ERROR_CODE

        // TODO Handle error
        if (error_code != NO_ERROR) {
            when (error_code) {
                ERROR_SERVER -> {
                    tripUiState = tripUiState.copy(error_field = "Server error")
                }

                else -> {
                    tripUiState = tripUiState.copy(error_field = "Bad request")
                }
            }
            ERROR(TagDebug.CREATE_TRIP, tripUiState.error_field)
            return EXIT_ERROR
        }

        updateUiState(tripUiState.tripDetails.copy(id = response.TRIP_ID))

        if (validateInput()) {
            DEBUG(TagDebug.CREATE_TRIP, "Insertion in the database")
            tripsRepository.insertTrip(tripUiState.tripDetails.toTrip())
        } else {
            ERROR(TagDebug.CREATE_TRIP, "Current Ui State not valid")
            return EXIT_ERROR
        }
        changeCurrentTripId(response.TRIP_ID)
        return response.TRIP_ID
    }

    /**
     * Change current trip id
     *
     * @param id
     */
    private fun changeCurrentTripId(id: Int) {
        DEBUG(TagDebug.CREATE_TRIP, "Change current Trip Id")
        val currentTrip = CurrentTrip.getInstance()
        currentTrip.updateCurrentTripCode(id)
    }

    /**
     * valid the current ui state
     *
     * @param uiState
     * @return if the current ui state is valid
     */
    private fun validateInput(uiState: TripDetails = tripUiState.tripDetails): Boolean {
        DEBUG(TagDebug.CREATE_TRIP, "Validating Trip Ui State")
        return with(uiState) {
            name.isNotBlank() &&
                    location.isNotBlank() &&
                    description.isNotBlank()
        }
    }

}

data class TripUiState(
    val tripDetails: TripDetails = TripDetails(),
    val isEntryValid: Boolean = false,
    val error_field: String = ""
)

