package com.example.oria.ui.view.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.view.settings.button
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.trip.GalleryViewModel

@Composable
fun GalleryPage(
    navController: NavController,
    viewModel: GalleryViewModel = viewModel(factory = AppViewModelProvider.TripFactory)
){
    val screen = rememberInfoScreen()
    val galleryUiState by viewModel.galleryUiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(screen.getDpHeight()),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        button(
            screen = screen, navController = navController, text = "Create a new trip",
            onClick = {
                navController.navigate("createTrip")
            }
        )
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                screen.getDpHeight(),
                Alignment.Top,
            ),
        ) {
            items(galleryUiState.tripList){trip ->
                button(
                    screen = screen, navController = navController, text = trip.name,
                    onClick = {
                        navController.navigate("currentTrip")
                    }
                )
            }
        }


    }

}

