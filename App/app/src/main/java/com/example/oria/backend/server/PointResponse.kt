package com.example.oria.backend.server

import com.example.oria.backend.data.storage.point.PointDetails
import kotlinx.serialization.Serializable

@Serializable
data class CreatePointResponse(
    val ERROR_CODE: Int,
    val POINT_ID: Int
)

@Serializable
data class ImportPointResponse(
    val ERROR_CODE: Int,
    val POINT: PointDetails
)

@Serializable
data class DeletePointResponse(
    val ERROR_CODE: Int
)