package com.example.oria.viewModel.trip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.TIMEOUT_MILLIS
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.data.storage.point.Point
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PointViewModel(
    savedStateHandle: SavedStateHandle,
    pointRepository: PointRepository
) :
    ViewModel() {

    private val pointId: Int = checkNotNull(savedStateHandle["pointId"])

    val pointUiState: StateFlow<PointUiState> = pointRepository.getPointStream(pointId).map{
        if(it == null){
            PointUiState()
        }else {
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