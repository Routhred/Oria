package com.example.oria

import android.app.Application
import android.location.Location
import com.example.oria.backend.data.storage.AppContainer
import com.example.oria.backend.data.storage.AppDataContainer
import com.example.oria.backend.data.storage.dataStore.PreferencesManager
import com.example.oria.backend.data.storage.point.PointDatabase
import com.example.oria.backend.data.storage.trip.TripDatabase
import com.example.oria.backend.server.OriaClient
import dagger.hilt.android.HiltAndroidApp



/**
 * Class for the application
 *
 */
@HiltAndroidApp
class OriaApplication : Application() {
    lateinit var container: AppContainer

    companion object{
        var location: Location = Location("location")
    }


    val tripDatabase: TripDatabase by lazy{
        TripDatabase.getDatabase(this)
    }
    val pointDatabase: PointDatabase by lazy{
        PointDatabase.getDatabase(this)
    }
    val preferencesManager: PreferencesManager by lazy{
        PreferencesManager.getManager(context = this)
    }



    override fun onCreate(){
        super.onCreate()
        val trip_communication = OriaClient.getInstance()

        container = AppDataContainer(this)
        trip_communication.tripsRepository = container.tripsRepository
        trip_communication.pointRepository = container.pointsRepository
    }
}