package com.example.oria.viewModel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrentTrip  {

    private var _currentTripId = MutableStateFlow(0)
    val currentTripId = _currentTripId.asStateFlow()
    companion object {

        @Volatile
        private var instance: CurrentTrip? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CurrentTrip().also { instance = it }
            }
    }
    fun updateCurrentTripCode(id: Int){
        _currentTripId.value = id
        Log.d("CurrentTrip::updateCurrentTripCode", _currentTripId.value.toString())
    }

    fun getTripId(): Int{
        return _currentTripId.value
    }


}