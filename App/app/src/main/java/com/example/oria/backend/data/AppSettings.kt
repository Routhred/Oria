package com.example.oria.backend.data

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings (
    var currentTripId: Int = 0
)

