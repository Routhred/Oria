package com.example.oria.viewModel.database

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.backend.data.storage.StoreData
import com.example.oria.backend.data.storage.trip.Trip
import com.example.oria.backend.data.storage.trip.TripRepository
import kotlinx.coroutines.launch

class TripEntryViewModel (
    private val tripsRepository: TripRepository,
    private val data: StoreData
): ViewModel(){

    var tripUiState by mutableStateOf(TripUiState())
        private set

    fun updateUiState(tripDetails: TripDetails){
        tripUiState = TripUiState(
            tripDetails = tripDetails,
            isEntryValid = validateInput(tripDetails)
        )
    }

    fun getUiState(fieldTrip: FieldTrip): String{
        return when(fieldTrip){
            FieldTrip.ID -> tripUiState.tripDetails.id.toString()
            else -> "Nothing"
        }
    }

    suspend fun saveItem(): Long{
        val tripId = if(validateInput()){
            tripsRepository.insertTrip(tripUiState.tripDetails.toTrip())
        }else{
            Log.w("Save Trip", "Input not valid")
            0L
        }
        changeCurrentTripId(tripId.toInt())
        return tripId
    }

    private fun changeCurrentTripId(id: Int){
        viewModelScope.launch{
            data.saveCurrentTripId(id)
        }
    }

    private fun validateInput(uiState: TripDetails = tripUiState.tripDetails): Boolean{
        return with(uiState){
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

data class TripDetails(
    val id: Int = 0,
    val location: String = "",
    val name: String = "",
    val date: String = "",
    val description: String = "",
    val points: String = ""
)

fun TripDetails.toTrip(): Trip = Trip(
    id = id,
    name = name,
    location = location,
    date = date,
    description = description,
    points = points
)

fun Trip.toTripUiState(isEntryValid: Boolean = false): TripUiState = TripUiState(
    tripDetails = this.toTripDetails(),
    isEntryValid = isEntryValid
)

fun Trip.toTripDetails(): TripDetails = TripDetails(
    id = id,
    name = name,
    location = location,
    date = date,
    description = description,
    points = points
)

enum class FieldTrip{
    ID, NAME, LOCATION, DATE, DESCRIPTION
}