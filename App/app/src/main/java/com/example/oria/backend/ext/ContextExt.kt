package com.example.oria.backend.ext

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.oria.MainActivity

fun Context.hasRequiredPermission(): Boolean {
    return MainActivity.CAMERAX_PERMISSIONS.all { it ->
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}