package com.example.oria.viewModel.global

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.backend.data.storage.trip.Trip
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.ui.theme.TIMEOUT_MILLIS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the home view
 *
 * @constructor
 *
 * @param tripRepository
 */
class HomeViewModel(tripRepository: TripRepository) : ViewModel() {

    // Get the last trip insert in the database (it is the current trip)
    val homeUiState: StateFlow<HomeUiState> = tripRepository.getLastStream().map {
        // If there is no trip in the database, we will show an empty trip
        if (it == null) {
            HomeUiState()
        } else {
            HomeUiState(it)
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )


}

data class HomeUiState(
    val currentTrip: Trip = Trip()
)