package com.example.oria.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.oria.ui.navigation.ScreenInfo
import com.example.oria.ui.navigation.rememberInfoScreen

@Composable
fun HomePage(navController: NavController) {
    val screen = rememberInfoScreen()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            screen.getDpHeight(),
            Alignment.Top,
        ),
        modifier = androidx.compose.ui.Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(vertical = screen.getDpHeight()),
    ) {
        currentTrip(screen = screen, navController = navController)
        tripGallery(screen = screen, navController)
        addPoint(screen = screen, navController = navController)
        profile(screen = screen, navController = navController)
        parameters(screen = screen, navController = navController)
    }
}

@Composable
fun currentTrip(screen: ScreenInfo, navController: NavController) {
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
        Text(text = "Current trip")
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
fun parameters(screen: ScreenInfo, navController: NavController) {
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(2)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = {
            navController.navigate("params")
        }
    ) {
        Text(text = "Parameters")
    }
}

