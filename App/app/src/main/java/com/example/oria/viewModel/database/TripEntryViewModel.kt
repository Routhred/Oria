package com.example.oria.viewModel.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.trip.CurrentTrip
import com.example.oria.backend.data.storage.trip.TripDetails
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.ERROR
import com.example.oria.backend.utils.TagDebug

/**
 * View Model for the new trip view
 *
 * @property tripsRepository
 */
class TripEntryViewModel(
    private val tripsRepository: TripRepository
) : ViewModel() {

    var tripUiState by mutableStateOf(TripUiState())
        private set

    /**
     * Update current ui state with new field
     *
     * @param tripDetails tripDetails with new field to update current ui state
     */
    fun updateUiState(tripDetails: TripDetails) {
        DEBUG(TagDebug.CREATE_TRIP, "Update Ui State")
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
    suspend fun saveItem(): Long {
        val tripId = if (validateInput()) {
            DEBUG(TagDebug.CREATE_TRIP, "Insertion in the database")
            tripsRepository.insertTrip(tripUiState.tripDetails.toTrip())
        } else {
            ERROR(TagDebug.CREATE_TRIP, "Current Ui State not valid")
            0L
        }
        changeCurrentTripId(tripId.toInt())
        return tripId
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
    val isEntryValid: Boolean = false
)

