package com.example.oria.ui.view.trip

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.OriaApplication
import com.example.oria.backend.camera.CameraViewModel
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.view.settings.button
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.database.PointEntryViewModel
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Composable
fun AddPointPage(
    navController: NavController,
    cameraViewModel: CameraViewModel,
    viewModel: PointEntryViewModel = viewModel(factory = AppViewModelProvider.PointFactory),
    tripId: Int? = 0
){
    val bitmap = cameraViewModel.getBitmap()
    println(cameraViewModel.photoTaken())
    val screen = rememberInfoScreen()
    val coroutine = rememberCoroutineScope()
    Column (
        verticalArrangement = Arrangement.spacedBy(screen.getDpHeight(0.5f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(size = 15.dp)
            )
            .fillMaxSize()
            .padding(vertical = screen.getDpHeight())
    ){
        Box(
            modifier = Modifier
                .clickable {
                    navController.navigate("camera")
                }
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(4))
                .background(
                    color = when (cameraViewModel.photoTaken()) {
                        true -> MaterialTheme.colorScheme.background
                        else -> MaterialTheme.colorScheme.tertiary
                    },
                    shape = RoundedCornerShape(size = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            var isImage by remember{
                mutableStateOf(false)
            }
            if(bitmap != null) {
                Image(
                    bitmap.asImageBitmap(),
                    contentDescription = "Profile picture",
                    modifier = Modifier.fillMaxSize()
                )
            }else{
                Text(text = "Click to take a photo")
            }
        }
        var errorText by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = viewModel.pointUiState.pointDetails.name,
            label = { Text(text = "Name") },
            onValueChange = { text ->
                viewModel.updateUiState(
                    viewModel.pointUiState.pointDetails.copy(name = text)
                )},
            modifier = Modifier
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(2)),
            singleLine = true,
            isError = isError
        )
        if(isError){
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
            )
        }

        OutlinedTextField(
            value = viewModel.pointUiState.pointDetails.description,
            label = { Text(text = "Description") },
            onValueChange = { text ->
                viewModel.updateUiState(
                    viewModel.pointUiState.pointDetails.copy(description = text)
                )},
            modifier = Modifier
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(4))
        )
        button(
            screen = screen,
            navController = navController,
            text = "Set GPS",
            height = 2,
            onClick = {
                viewModel.updateUiState(
                    viewModel.pointUiState.pointDetails.copy(
                        location =
                        "${OriaApplication.location.latitude};" +
                                "${OriaApplication.location.longitude}")
                )
            }
        )
        val context = LocalContext.current
        val imageName = viewModel.getImageName()

        button(
            screen = screen,
            navController = navController,
            text = "Save",
            height = 2,
            onClick = {
                val savedImagePath = saveImageToInternalStorage(context, bitmap, imageName)
                coroutine.launch{
                    viewModel.updateUiState(
                        viewModel.pointUiState.pointDetails.copy(image = when(savedImagePath){
                            null -> " "
                            else -> savedImagePath
                        })
                    )
                    viewModel.saveItem()
                    navController.popBackStack()
                }



            }
        )
        button(
            screen = screen,
            navController = navController,
            text = "Cancel",
            height = 2,
            onClick = {
                navController.popBackStack()
            },
            color = MaterialTheme.colorScheme.error
        )
    }
}


fun saveImageToInternalStorage(context: Context, bitmap: Bitmap?, fileName: String): String?{
    val file = File(context.filesDir, fileName)
    if(bitmap != null) {
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }else{
        return "No Bitmap"
    }
}

