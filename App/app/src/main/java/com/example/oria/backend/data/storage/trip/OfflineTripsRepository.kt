package com.example.oria.backend.data.storage.trip
import kotlinx.coroutines.flow.Flow

class OfflineTripsRepository(private val tripDao: TripDao) : TripRepository {
    override fun getAllTripsStream(): Flow<List<Trip>> = tripDao.getAllTrips()

    override fun getTripStream(id: Int): Flow<Trip?> = tripDao.getTrip(id)
    override fun getLastStream(): Flow<Trip?> = tripDao.getLastTrip()

    override suspend fun insertTrip(item: Trip) = tripDao.createTrip(item)

    override suspend fun deleteTrip(item: Trip) = tripDao.removeTrip(item)

    override suspend fun updateTrip(item: Trip) = tripDao.updateTrip(item)
}