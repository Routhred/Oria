package com.example.oria.backend.utils

import android.util.Log

/**
 * Data class to store the TAG for debugging
 *
 * @property tag
 */
enum class TagDebug(val tag: String) {
    STORAGE("Storage"),

    CHANGE_VIEW("Change view"),
    CREATE_POINT("Create Point"),
    CREATE_TRIP("Create Trip"),
    HOME_INFO("Home info"),

    LOGIN("Login"),
    SERVER("Server"),
    REGISTER("Register"),

    GPS("GPS"),
    CAMERA("Camera"),
    PERMISSIONS("Pemissions"),

    STRING_OPERATION("String operation"),
    BEGIN_FUNCTION("Begin Function")
}


/**
 * Debug function
 *
 * @param tag TagDebug for uniform debugging
 * @param str Debug to display
 */
fun DEBUG(tag: TagDebug, str: String) {
    Log.d(tag.toString(), str)
}

/**
 * ERROR function
 *
 * @param tag
 * @param str
 */
fun ERROR(tag: TagDebug, str: String) {
    Log.e(tag.toString(), str)
}
