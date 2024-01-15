package com.example.oria.backend.data.storage.trip

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

class CurrentTrip {

    private var _currentTripId = MutableStateFlow(0)

    companion object {

        @Volatile
        private var instance: CurrentTrip? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CurrentTrip().also { instance = it }
            }
    }

    fun updateCurrentTripCode(id: Int) {
        _currentTripId.value = id
        Log.d("CurrentTrip::updateCurrentTripCode", _currentTripId.value.toString())
    }
}