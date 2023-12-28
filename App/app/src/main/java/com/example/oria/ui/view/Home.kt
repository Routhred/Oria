package com.example.oria.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.backend.location.LocationService
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.ui.navigation.ScreenInfo
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.viewModel.HomeViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.TripFactory)
) {

    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val currentTripId = homeUiState.currentTrip.id
    val currentTripName = homeUiState.currentTrip.name

    val screen = rememberInfoScreen()
    Log.d("currentTripName", currentTripName)
    Log.d("Test getcurrentTripID", currentTripId.toString())
    Log.d("Launch GPS", "Call to function")

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
        currentTrip(
            screen = screen,
            navController = navController,
            text = currentTripName,
            tripId = currentTripId)
        tripGallery(screen = screen, navController)
        addPoint(screen = screen, navController = navController, currentTripId)
        profile(screen = screen, navController = navController)
        settings(screen = screen, navController = navController)
    }
}

@Composable
fun currentTrip(
    screen: ScreenInfo,
    navController: NavController,
    text: String = "No Current Trip",
    tripId: Int = 0
) {

    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("currentTrip/${tripId}")
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
fun addPoint(screen: ScreenInfo, navController: NavController, tripId: Int) {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            Intent(context, LocationService::class.java).apply{
                action = LocationService.ACTION_START
                context.startService(this)
            }
            navController.navigate("addPoint/${tripId}")
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





