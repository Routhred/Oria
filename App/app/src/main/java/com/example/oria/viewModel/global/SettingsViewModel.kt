package com.example.oria.viewModel.global

import androidx.lifecycle.ViewModel
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.data.storage.trip.TripRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val pointsRepository: PointRepository,
    private val tripRepository: TripRepository
) : ViewModel() {

    suspend fun logout() {
        coroutineScope {
            launch {
                callLogout()
            }

        }
    }

    private suspend fun callLogout() {
        pointsRepository.nukeTable()
        tripRepository.nukeTable()
    }
}