package com.example.oria.viewModel.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.data.storage.trip.TripDetails
import com.example.oria.backend.server.OriaClient
import kotlinx.coroutines.launch

/**
 * View Model for the new trip view
 *
 * @property preferencesManager
 */
class ImportTripViewModel(
    preferencesManager: PreferencesManager
) : ViewModel() {
    private val preferencesManager = preferencesManager
    var importTripState by mutableStateOf(ImportTripState())
        private set

    /**
     * Update current ui state with new field
     *
     * @param trip_id
     */
    fun updateUiState(trip_id: Int) {
        importTripState = ImportTripState(
            trip_id = trip_id
        )
    }

    fun importTrip(navController: NavController){
        val tripCommunication = OriaClient.getInstance()
        viewModelScope.launch{
            tripCommunication.callImportTrip(importTripState.trip_id, preferencesManager.getData(PreferencesKey.USERNAME,""))
        }
        navController.navigate("main")
    }

}

data class ImportTripState(
    val trip_id: Int = 0,
    val error_field: String = "",
    val tripDetails: TripDetails = TripDetails()
)