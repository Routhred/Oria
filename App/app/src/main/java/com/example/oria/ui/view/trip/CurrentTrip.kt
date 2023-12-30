package com.example.oria.ui.view.trip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.R
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.navigation.ScreenInfo
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.theme.loginFontFamily
import com.example.oria.ui.view.settings.button
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.trip.Points
import com.example.oria.viewModel.trip.TripViewModel

@Composable
fun CurrentTripPage(
    navController: NavController,
    tripId: Int? = 0,
    tripViewModel: TripViewModel = viewModel(factory = AppViewModelProvider.factory)
) {

    val homeUiState by tripViewModel.tripUiState.collectAsState()
    val pointUiState by tripViewModel.pointUiState.collectAsState()

    val trip = homeUiState.currentTrip
    val screen = rememberInfoScreen()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.logo_round),
            contentDescription = "Profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screen.getDpHeight(2))
                .background(
                    color = MaterialTheme.colorScheme.secondary
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(screen.getDpHeight(16))
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(size = 88.dp),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                screen.getDpHeight(),
                Alignment.Top,
            ),

            ) {
            Text(
                text = "${trip.location}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = loginFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "Code : ${trip.id}",
                textAlign = TextAlign.Left
            )
            Text(
                text = trip.description,
                textAlign = TextAlign.Left
            )
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    screen.getDpHeight(),
                    Alignment.Top,
                ),
            ) {
                button(
                    screen = screen,
                    navController = navController,
                    text = "Create a new point",
                    height = 1,
                    onClick = { navController.navigate("addPoint/${tripId}") }
                )
                displayAllPoint(
                    navController = navController,
                    screen = rememberInfoScreen(),
                    points = pointUiState
                )


            }
        }

    }
}

@Composable
fun displayAllPoint(navController: NavController, screen: ScreenInfo, points: Points) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            screen.getDpHeight(),
            Alignment.Top,
        ),
    ) {
        items(points.points) { point ->
            button(
                screen = screen,
                navController = navController,
                text = point.name,
                height = 2,
                onClick = {
                    DEBUG(TagDebug.CHANGE_VIEW, "point/${point.id}")
                    navController.navigate("point/${point.id}")
                }
            )
        }
    }
}