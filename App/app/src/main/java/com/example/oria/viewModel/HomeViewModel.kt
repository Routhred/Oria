package com.example.oria.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oria.backend.data.storage.StoreData
import com.example.oria.backend.data.storage.trip.Trip
import com.example.oria.backend.data.storage.trip.TripRepository
import com.example.oria.viewModel.OriaViewModel.Companion.TIMEOUT_MILLIS
import com.example.oria.viewModel.database.TripDetails
import com.example.oria.viewModel.database.toTripDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val tripRepository: TripRepository,
    private val data: StoreData
) : ViewModel() {

    private val homeUiState: StateFlow<HomeUiState> = data.getCurrentTripID.map{
        HomeUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )

    val uiState: StateFlow<TripDetailsUiState> =
        tripRepository.getTripStream(homeUiState.value.currentTripId)
            .filterNotNull()
            .map {
                TripDetailsUiState(it.toTripDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TripDetailsUiState()
            )

    fun getCurrentTripID(): Int{
        val tmpUiState: StateFlow<HomeUiState> = data.getCurrentTripID.map{
            HomeUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
        return tmpUiState.value.currentTripId
    }
}

data class TripDetailsUiState(
    val trip: TripDetails = Trip().toTripDetails()
)

data class HomeUiState(
    val currentTripId: Int = 0
)