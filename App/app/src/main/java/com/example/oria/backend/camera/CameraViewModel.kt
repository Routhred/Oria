package com.example.oria.backend.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

/**
 * View Model for the camera view
 *
 */
class CameraViewModel : ViewModel() {
    private var _bitmap: Bitmap? = null
    lateinit var navController: NavController

    fun onTakePhoto(bitmap: Bitmap) {
        _bitmap = bitmap
        navController.popBackStack()
    }

    fun photoTaken(): Boolean {
        return _bitmap != null
    }

    fun getBitmap(): Bitmap? {
        return _bitmap
    }
}
