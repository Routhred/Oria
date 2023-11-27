package com.example.oria.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.oria.ui.view.auth.LoginPage
import com.example.oria.ui.view.auth.PasswordPage
import com.example.oria.ui.view.auth.RegisterPage
import com.example.oria.ui.view.HomePage
import com.example.oria.ui.view.parameters.AccountPage
import com.example.oria.ui.view.parameters.ParametersPage
import com.example.oria.ui.view.parameters.ProfilePage
import com.example.oria.ui.view.trip.AddPointPage
import com.example.oria.ui.view.trip.CreateTripPage
import com.example.oria.ui.view.trip.CurrentTripPage
import com.example.oria.ui.view.trip.GalleryPage
import com.example.oria.ui.view.trip.ImportTripPage
import com.example.oria.ui.view.trip.PointPage

@Composable
fun NavigationGraph(ctx: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "auth") {
        navigation(
            startDestination = "login",
            route = "auth",
        ) {
            composable(route = Screen.LoginScreen.route) {
                LoginPage(navController = navController)
            }
            composable(route = Screen.RegisterScreen.route) {
                RegisterPage(navController = navController)
            }
            composable(route = Screen.PasswordScreen.route) {
                PasswordPage(navController = navController)
            }
        }
        navigation(
            startDestination = "home",
            route = "main",
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomePage(navController = navController)
            }
            composable(route = Screen.CurrentTripScreen.route){
                CurrentTripPage(navController = navController)
            }

        }
        navigation(
            startDestination = "parameters",
            route = "params"
        ){
            composable(route = Screen.ParametersScreen.route) {
                ParametersPage(navController = navController)
            }

            composable(route = Screen.AccountScreen.route) {
                AccountPage(navController = navController)
            }

        }
        navigation(
            startDestination = "gallery",
            route = "trip"
        ){
            composable(route = Screen.GalleryScreen.route){
                GalleryPage(navController = navController)
            }
            composable(route = Screen.ImportTripScreen.route){
                ImportTripPage(navController = navController)
            }
            composable(route = Screen.CreateTripScreen.route){
                CreateTripPage(navController = navController)
            }
            composable(route = Screen.PointScreen.route){
                PointPage(navController = navController)
            }
        }
        composable(route = Screen.AddPointScreen.route){
            AddPointPage(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfilePage(navController = navController)
        }
       /*navigation(
            startDestination = "picture_screen",
            route = "picture",
        ) {
            composable(route = Screen.PictureScreen.route) {
                val viewModel = it.sharedViewModel<CameraViewModel>(navController)
                viewModel.navController = navController
                PicturePage(ctx, navController = navController)
            }
            composable(route = Screen.CameraScreen.route) {
                val viewModel = it.sharedViewModel<CameraViewModel>(navController)
                CameraPage(navController = navController, viewModel)
            }
            composable(route = Screen.PhotoScreen.route) {
                val viewModel = it.sharedViewModel<CameraViewModel>(navController)
                DisplayPhoto(navController = navController, viewModel)
            }
        }*/
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
