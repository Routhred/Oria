package com.example.oria.backend.data.storage.trip

import kotlinx.coroutines.flow.Flow

interface TripRepository {
    fun getAllTripsStream(): Flow<List<Trip>>
    fun getTripStream(id: Int): Flow<Trip?>
    fun getLastStream(): Flow<Trip?>
    suspend fun insertTrip(item: Trip)
    suspend fun deleteTrip(item: Trip)
    suspend fun updateTrip(item: Trip)
}