package com.example.oria.backend.data.storage

import android.content.Context
import com.example.oria.backend.data.storage.point.OfflinePointsRepository
import com.example.oria.backend.data.storage.point.PointDatabase
import com.example.oria.backend.data.storage.point.PointRepository
import com.example.oria.backend.data.storage.trip.OfflineTripsRepository
import com.example.oria.backend.data.storage.trip.TripDatabase
import com.example.oria.backend.data.storage.trip.TripRepository

interface AppContainer {
    val tripsRepository: TripRepository
    val pointsRepository: PointRepository
}


class AppDataContainer (private val context: Context) : AppContainer {
    override val tripsRepository: TripRepository by lazy {
        OfflineTripsRepository(TripDatabase.getDatabase(context).itemDao())
    }
    override val pointsRepository: PointRepository by lazy {
        OfflinePointsRepository(PointDatabase.getDatabase(context).itemDao())
    }
}