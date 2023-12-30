package com.example.oria.backend.ext

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Function to check if the permissions are granted
 *
 * @param array Array with the permissions to check
 * @return true or false
 */
fun Context.hasRequiredPermission(array: Array<String>): Boolean {
    DEBUG(TagDebug.PERMISSIONS, "Check permissions")
    return array.all { permission ->

        val temp = ContextCompat.checkSelfPermission(
            this,
            permission
        )
        println("$permission, $temp")
        temp == PackageManager.PERMISSION_GRANTED

    }
}

/**
 * Get the activity link to the context, return null if the context is not link to an activity
 *
 * @return
 */
fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

/**
 * Load an image from the storage
 *
 * @param imagePath
 * @return
 */
fun Context.loadImageFromInternalStorage(imagePath: String): Bitmap? {
    DEBUG(TagDebug.STORAGE, "Try to load image : $imagePath")
    try {
        val file = File(imagePath)
        val stream = FileInputStream(file)
        return BitmapFactory.decodeStream(stream)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}