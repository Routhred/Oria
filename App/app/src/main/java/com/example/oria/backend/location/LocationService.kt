package com.example.oria.backend.location

import android.app.Service
import android.content.Intent
import android.health.connect.datatypes.AppInfo
import android.os.IBinder
import android.util.Log
import com.example.oria.OriaApplication
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        Log.d("Location", "Start Location")
        locationClient
            .getLocationUpdates(10000L)
            .catch { e ->
                println("Error " + e.message)
            }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                OriaApplication.location = location
                println("Location : ($lat,$long)")
            }
            .launchIn(serviceScope)
    }
    private fun stop() {
        Log.d("Location", "Stop Location")
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
