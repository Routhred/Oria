package com.example.oria.viewModel.trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.TIMEOUT_MILLIS
import com.example.oria.backend.data.storage.trip.Trip
import com.example.oria.backend.data.storage.trip.TripRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GalleryViewModel(tripRepository: TripRepository) : ViewModel() {
    val galleryUiState: StateFlow<GalleryUiState> = tripRepository.getAllTripsStream().map{
        GalleryUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = GalleryUiState()
    )
}

data class GalleryUiState(val tripList: List<Trip> = listOf())