package com.example.oria.backend.server

import kotlinx.serialization.Serializable

@Serializable
data class CreatePointResponse(
    val ERROR_CODE: Int,
    val POINT_ID: Int
)