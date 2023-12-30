package com.example.oria.backend.location

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.oria.OriaApplication
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Class for the location service
 *
 */
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

    /**
     * Start the location service
     *
     */
    private fun start() {
        DEBUG(TagDebug.GPS, "Start service")
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

    /**
     * Stop the locaiton service
     *
     */
    private fun stop() {
        DEBUG(TagDebug.GPS, "Stop service")
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}

/**
 * Start GPS Service Function, to call at the beginning of the app
 *
 * @param context
 */
fun launchGPS(context: Context) {
    DEBUG(TagDebug.GPS, "Start Service")
    Intent(context, LocationService::class.java).apply {
        action = LocationService.ACTION_START
        context.startService(this)
    }
}
