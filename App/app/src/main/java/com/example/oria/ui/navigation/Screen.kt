package com.example.oria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.oria.ui.theme.*

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login")
    object RegisterScreen : Screen("register")
    object HomeScreen : Screen("home")
    object PasswordScreen : Screen("password")
    object ParametersScreen : Screen("parameters")
    object ProfileScreen : Screen("profile")
    object AccountScreen : Screen("account")
    object GalleryScreen : Screen("gallery")

    object ImportTripScreen : Screen("importTrip")
    object CreateTripScreen : Screen("createTrip")
    object AddPointScreen : Screen("addPoint")
    object CurrentTripScreen : Screen("currentTrip")
    object PointScreen : Screen("point")
}

@Composable
fun rememberInfoScreen(): ScreenInfo {
    val configuration = LocalConfiguration.current
    return ScreenInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> ScreenInfo.ScreenType.Compact
            configuration.screenWidthDp < 840 -> ScreenInfo.ScreenType.Medium
            else -> ScreenInfo.ScreenType.Expended
        },
        sceenHeightInfo = when {
            configuration.screenHeightDp < 480 -> ScreenInfo.ScreenType.Compact
            configuration.screenHeightDp < 900 -> ScreenInfo.ScreenType.Medium
            else -> ScreenInfo.ScreenType.Expended
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp,
    )
}

data class ScreenInfo(
    val screenWidthInfo: ScreenType,
    val sceenHeightInfo: ScreenType,
    val screenWidth: Dp,
    val screenHeight: Dp,
) {

    fun getDpWidth(n: Int = 1): Dp {
        return screenWidth.div(GRID_COLUMN).times(n)
    }
    fun getDpHeight(n: Int = 1): Dp {
        return screenHeight.div(GRID_ROW).times(n)
    }

    fun getDpWidth(n: Float): Dp {
        return screenWidth.div(GRID_COLUMN).times(n)
    }
    fun getDpHeight(n: Float): Dp {
        return screenHeight.div(GRID_ROW).times(n)
    }
    sealed class ScreenType {
        object Compact : ScreenType()
        object Medium : ScreenType()
        object Expended : ScreenType()
    }
}
