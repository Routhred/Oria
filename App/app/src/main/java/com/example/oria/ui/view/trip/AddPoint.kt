package com.example.oria.ui.view.trip

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.backend.camera.CameraViewModel
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.view.settings.button
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.database.PointEntryViewModel
import kotlinx.coroutines.launch

@Composable
fun AddPointPage(
    navController: NavController,
    cameraViewModel: CameraViewModel,
    databaseViewModel: PointEntryViewModel = viewModel(factory = AppViewModelProvider.PointFactory)
){
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
                    color = when(cameraViewModel.photoTaken()){
                        true -> MaterialTheme.colorScheme.background
                        else -> MaterialTheme.colorScheme.tertiary
                    },
            shape = RoundedCornerShape(size = 15.dp)),
            contentAlignment = Alignment.Center
        ){
            var isImage by remember{
                mutableStateOf(false)
            }
            val bitmap = cameraViewModel.getBitmap()
            println(cameraViewModel.photoTaken())
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
            value = databaseViewModel.pointUiState.pointDetails.name,
            label = { Text(text = "Name") },
            onValueChange = { text ->
                databaseViewModel.updateUiState(
                    databaseViewModel.pointUiState.pointDetails.copy(name = text)
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
            value = databaseViewModel.pointUiState.pointDetails.description,
            label = { Text(text = "Description") },
            onValueChange = { text ->
                databaseViewModel.updateUiState(
                    databaseViewModel.pointUiState.pointDetails.copy(description = text)
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
                // TODO GPS
            }
        )
        button(
            screen = screen,
            navController = navController,
            text = "Save",
            height = 2,
            onClick = {
                databaseViewModel.updateUiState(
                    databaseViewModel.pointUiState.pointDetails.copy(tripCode = 10)
                )
                coroutine.launch{
                    databaseViewModel.saveItem()
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


