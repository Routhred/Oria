package com.example.oria.backend.ext

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.oria.MainActivity

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