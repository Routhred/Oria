package com.example.oria.backend.server

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val ERROR_CODE: Int,
    val TOKEN: String
)
