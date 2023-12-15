package com.example.oria.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.TIMEOUT_MILLIS
import com.example.oria.backend.data.storage.trip.Trip
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.viewModel.trip.GalleryUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(tripRepository: TripRepository) : ViewModel() {

    // Get the last trip insert in the database (it is the current trip)
    val homeUiState: StateFlow<HomeUiState> = tripRepository.getLastStream().map{
        HomeUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )



}
data class HomeUiState(
    val currentTrip: Trip = Trip()
)
