package com.example.oria.backend.ext

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.oria.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

fun Context.hasRequiredPermission(array: Array<String>): Boolean {
    return array.all { permission ->

        val temp = ContextCompat.checkSelfPermission(
            this,
            permission
        )
        println("$permission, $temp")
        temp == PackageManager.PERMISSION_GRANTED

    }
}
fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

