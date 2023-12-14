package com.example.oria.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.backend.data.storage.StoreData
import com.example.oria.ui.navigation.ScreenInfo
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.viewModel.HomeViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.TripFactory)
) {

    val screen = rememberInfoScreen()
    val currentTripName = homeViewModel.uiState.collectAsState().value.trip.name
    Log.d("currentTripName", currentTripName)
    Log.d("Test getcurrentTripID", homeViewModel.getCurrentTripID().toString())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            screen.getDpHeight(),
            Alignment.Top,
        ),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(vertical = screen.getDpHeight()),
    ) {
        currentTrip(screen = screen, navController = navController, text = homeViewModel.uiState
            .value.trip.name)
        tripGallery(screen = screen, navController)
        addPoint(screen = screen, navController = navController)
        profile(screen = screen, navController = navController)
        settings(screen = screen, navController = navController)
    }
}

@Composable
fun currentTrip(screen: ScreenInfo, navController: NavController, text: String = "No Current " +
        "Trip") {

    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("currentTrip")
        }
    ) {
        Text(text = text)
    }
}

@Composable
fun tripGallery(screen: ScreenInfo, navController: NavController) {
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("trip")
        }
    ) {
        Text(text = "Trip gallery")
    }
}

@Composable
fun addPoint(screen: ScreenInfo, navController: NavController) {
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("addPoint")
        }
    ) {
        Text(text = "Add Point")
    }
}

@Composable
fun profile(screen: ScreenInfo, navController: NavController) {
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("profile")
        }
    ) {
        Text(text = "Profile")
    }
}

@Composable
fun settings(screen: ScreenInfo, navController: NavController) {
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("settings")
        }
    ) {
        Text(text = "Settings")
    }
}

