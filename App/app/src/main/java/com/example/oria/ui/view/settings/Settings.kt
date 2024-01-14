package com.example.oria.ui.view.settings

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.ui.navigation.ScreenInfo
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.global.SettingsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val screen = rememberInfoScreen()
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
        button(
            screen = screen,
            navController = navController,
            text = "Account",
            onClick = { navController.navigate("account") })
        button(
            screen = screen,
            navController = navController,
            text = "Profile",
            onClick = { navController.navigate("profile") })
        button(
            screen = screen,
            navController = navController,
            text = "Settings")
        button(
            screen = screen,
            navController = navController,
            text = "Disconnect",
            color = MaterialTheme.colorScheme.error,
            onClick = {
                GlobalScope.launch{
                    viewModel.logout()
                }
                navController.navigate("auth")
            })
    }
}

@Composable
fun button(
    screen: ScreenInfo,
    navController: NavController,
    text: String,
    height: Int = 4,
    width: Int = 7,
    onClick: () -> Unit = {navController.navigate("main")},
    color: Color = MaterialTheme.colorScheme.tertiary,
    enable: Boolean = true) {
    Button(
        modifier = Modifier
            .width(screen.getDpWidth(width))
            .height(screen.getDpHeight(height)),
        shape = RoundedCornerShape(size = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = onClick,
        enabled = enable
    ) {
        Text(text = text)
    }
}



