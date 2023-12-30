package com.example.oria.viewModel.trip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.backend.data.storage.point.Point
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.data.storage.trip.Trip
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.ui.theme.TIMEOUT_MILLIS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the current trip view
 *
 * @constructor
 *
 *
 * @param savedStateHandle
 * @param tripRepository
 * @param pointRepository
 */
class TripViewModel (
    savedStateHandle: SavedStateHandle,
    tripRepository: TripRepository,
    pointRepository: PointRepository
) : ViewModel() {

    private val tripId: Int = checkNotNull(savedStateHandle["tripId"])

    val tripUiState: StateFlow<TripUiState> = tripRepository.getTripStream(tripId).map{
        if(it == null){
            TripUiState()
        }else{
            TripUiState(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = TripUiState()
    )
    val pointUiState: StateFlow<Points> = pointRepository.getTripPoints(tripId).map{
        Points(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = Points()
    )

}

/**
 * Data class for the currentTrip Ui state
 *
 * @property currentTrip
 */
data class TripUiState(
    val currentTrip: Trip = Trip()
)
data class Points( val points: List<Point> = listOf())