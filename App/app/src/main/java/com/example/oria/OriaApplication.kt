package com.example.oria

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import com.example.oria.backend.data.storage.AppContainer
import com.example.oria.backend.data.storage.AppDataContainer
import com.example.oria.backend.data.storage.point.PointDatabase
import com.example.oria.backend.data.storage.trip.TripDatabase

const val TIMEOUT_MILLIS = 5_00L

class OriaApplication : Application() {
    lateinit var container: AppContainer


    val tripDatabase: TripDatabase by lazy{
        TripDatabase.getDatabase(this)
    }
    val pointDatabase: PointDatabase by lazy{
        PointDatabase.getDatabase(this)
    }

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}