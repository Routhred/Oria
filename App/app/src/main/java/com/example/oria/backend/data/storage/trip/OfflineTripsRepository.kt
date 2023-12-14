package com.example.oria.backend.data.storage.trip

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.example.oria.viewModel.OriaViewModel
import com.example.oria.viewModel.TripDetailsUiState
import com.example.oria.viewModel.database.toTripDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class OfflineTripsRepository(private val tripDao: TripDao) : TripRepository {
    override fun getAllTripsStream(): Flow<List<Trip>> = tripDao.getAllTrips()

    override fun getTripStream(id: Int): Flow<Trip> = tripDao.getTrip(id)

    override suspend fun insertTrip(item: Trip) = tripDao.createTrip(item)

    override suspend fun deleteTrip(item: Trip) = tripDao.removeTrip(item)

    override suspend fun updateTrip(item: Trip) = tripDao.updateTrip(item)
}