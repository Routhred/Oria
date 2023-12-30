package com.example.oria.viewModel.trip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.backend.data.storage.point.Point
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.ui.theme.TIMEOUT_MILLIS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the Point view
 *
 * @constructor
 *
 * @param savedStateHandle
 * @param pointRepository
 */
class PointViewModel(
    savedStateHandle: SavedStateHandle,
    pointRepository: PointRepository
) :
    ViewModel() {

    private val pointId: Int = checkNotNull(savedStateHandle["pointId"])

    val pointUiState: StateFlow<PointUiState> = pointRepository.getPointStream(pointId).map {
        if (it == null) {
            PointUiState()
        } else {
            PointUiState(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = PointUiState(Point())
    )

}

data class PointUiState(
    val currentPoint: Point = Point()
)