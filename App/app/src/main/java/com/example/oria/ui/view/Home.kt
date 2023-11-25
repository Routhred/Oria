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
import androidx.compose.material3.MaterialTheme
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
        currentTrip(screen = screen)
        widget_3(screen = screen)
        widget_1(screen = screen)
        widget_6(screen = screen, navController = navController)
    }
}

@Composable
fun currentTrip(screen: ScreenInfo) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .width(screen.getDpWidth(13))
            .height(screen.getDpHeight(0.15f)),
    ) {
    }
}

@Composable
fun tripGallery(screen: ScreenInfo) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .width(screen.getDpWidth(8))
            .height(screen.getDpHeight(10)),

    ) {
    }
}

@Composable
fun widget_4(screen: ScreenInfo, navController: NavController) {
    Box(
        modifier = Modifier
            .width(screen.getDpWidth(4))
            .height(screen.getDpHeight(4))
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(15.dp),
    ) {
    }
}

@Composable
fun widget_5(screen: ScreenInfo, navController: NavController) {
    Box(
        modifier = Modifier
            .width(screen.getDpWidth(4))
            .height(screen.getDpHeight(4))
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 15.dp),
            ),

    ) {
    }
}

@Composable
fun widget_1(screen: ScreenInfo) {
    Box(
        modifier = Modifier
            .width(screen.getDpWidth(4))
            .height(screen.getDpHeight(5))
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(15.dp),
    ) {
    }
}

@Composable
fun widget_6(screen: ScreenInfo, navController: NavController) {
    Box(
        modifier = Modifier
            .width(screen.getDpWidth(7))
            .height(screen.getDpHeight(9))
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(15.dp),
    ) {
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    val navController = rememberNavController()
    HomePage(navController)
}
