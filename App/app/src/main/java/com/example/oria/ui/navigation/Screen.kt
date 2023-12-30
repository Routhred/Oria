package com.example.oria.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.oria.ui.theme.*

/**
 * Class to store all screen route
 *
 * @property route Name of the route
 */
sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login")
    data object RegisterScreen : Screen("register")
    data object HomeScreen : Screen("home")
    data object PasswordScreen : Screen("password")
    data object SettingsScreen : Screen("settings_screen")
    data object ProfileScreen : Screen("profile")
    data object AccountScreen : Screen("account")
    data object GalleryScreen : Screen("gallery")
    data object ImportTripScreen : Screen("importTrip")
    data object CreateTripScreen : Screen("createTrip")
    data object AddPointScreen : Screen("addPoint")
    data object CurrentTripScreen : Screen("currentTrip")
    data object PointScreen : Screen("point")
    data object CameraScreen : Screen("camera")
}


/**
 * Function to get the info of the current screen
 *
 * @return the screen info as ScreenInfo
 */
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

/**
 * Class representing the screen and his infos
 *
 * @property screenWidthInfo
 * @property sceenHeightInfo
 * @property screenWidth
 * @property screenHeight
 */
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
