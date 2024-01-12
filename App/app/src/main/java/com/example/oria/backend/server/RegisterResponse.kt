package com.example.oria.backend.server

import kotlinx.serialization.Serializable

/**
 * Class to parse json response for register
 *
 * @property ERROR_CODE
 * @property TOKEN
 */
@Serializable
data class RegisterResponse(
    val ERROR_CODE: Int,
    val TOKEN: String
)
