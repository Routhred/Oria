package com.example.oria.backend.server

import com.example.oria.backend.data.storage.trip.TripDetails
import kotlinx.serialization.Serializable

@Serializable
data class CreateTripResponse(
    val ERROR_CODE: Int,
    val TRIP_ID: Int
)

@Serializable
data class ImportTripResponse(
    val ERROR_CODE: Int,
    val TRIP: TripDetails,
    val POINTS: List<Int>
)