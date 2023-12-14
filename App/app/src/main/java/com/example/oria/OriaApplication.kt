package com.example.oria

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import com.example.oria.backend.data.AppSettingsSerializer
import com.example.oria.backend.data.storage.AppContainer
import com.example.oria.backend.data.storage.AppDataContainer
import com.example.oria.backend.data.storage.StoreData
import com.example.oria.backend.data.storage.trip.TripDatabase

/*private const val CURRENT_TRIP_SETTINGS = "current_trip_settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = CURRENT_TRIP_SETTINGS
)*/

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)


class OriaApplication : Application() {
    lateinit var container: AppContainer
    lateinit var storeData: StoreData

    val database: TripDatabase by lazy{
        TripDatabase.getDatabase(this)
    }

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
        storeData = StoreData(dataStore)
        Log.i("App Creation", "End onCreate")
    }
}