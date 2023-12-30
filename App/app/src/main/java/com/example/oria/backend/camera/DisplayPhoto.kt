package com.example.oria.backend.camera

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun DisplayPhoto(
    navController: NavController,
    viewModel: CameraViewModel,
    context: Context = LocalContext.current
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val bitmap: Bitmap? = viewModel.getBitmap()
            if (bitmap == null) {
                Text(text = "No photo")
            } else {
                Image(
                    bitmap.asImageBitmap(),
                    contentDescription = "Photo taken"
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                }
            ) {
                Text(text = "Send Photo")
            }
            Button(
                onClick = {
                    navController.navigate("camera_screen")
                }
            ) {
                Text(text = "Take new photo")
            }
        }
    }
}
