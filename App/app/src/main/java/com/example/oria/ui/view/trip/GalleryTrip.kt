package com.example.oria.ui.view.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oria.ui.navigation.ScreenInfo
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.view.parameters.button

@Composable
fun GalleryPage(navController: NavController){
    val screen = rememberInfoScreen()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(
                    color = MaterialTheme.colorScheme.background,
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                screen.getDpHeight(),
                Alignment.Top,
            ),
        ) {
            displayAllTrip(navController = navController, screen = rememberInfoScreen())
            button(
                screen = screen, navController = navController, text = "Create a new trip",
                onClick = {navController.navigate("createTrip")})

        }
    }
}

@Composable
fun displayAllTrip(navController: NavController, screen: ScreenInfo){

}
