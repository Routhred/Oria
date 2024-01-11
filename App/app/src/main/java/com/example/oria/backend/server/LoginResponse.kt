package com.example.oria.backend.server

import kotlinx.serialization.Serializable

/**
 * Class to parse json response for login
 *
 * @property ERROR_CODE
 */
@Serializable
data class LoginResponse(
    val ERROR_CODE: Int
)
