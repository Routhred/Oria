package com.example.oria.backend.data.storage

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.oria.backend.data.storage.trip.Trip
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class StoreData (
    private val dataStore: DataStore<Preferences>
){

    init{
        Log.i("App Creation", "StoreData Creation")
    }
    companion object{
        val CURRENT_TRIP_ID_KEY = intPreferencesKey("current_trip_id")
        const val TAG = "StoreDataRepo"
    }

    val getCurrentTripID: Flow<Int> = dataStore.data.catch{
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            Log.d(TAG, "Get CurrentTripID")
            throw it
        }
    }
    .map { preferences ->
        preferences[CURRENT_TRIP_ID_KEY] ?: 0
    }

    suspend fun saveCurrentTripId(id: Int){
        dataStore.edit {
            preferences ->
            preferences[CURRENT_TRIP_ID_KEY] = id
        }
        Log.i("Save trip", "Last trip id is now : $id")
    }
}