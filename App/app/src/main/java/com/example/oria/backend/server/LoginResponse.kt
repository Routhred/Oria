package com.example.oria.backend.server

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val ERROR_CODE: Int,
    val TRIPS: List<Int>
)
