package com.example.oria

import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.oria.backend.ext.hasRequiredPermission
import com.example.oria.backend.server.OriaClient
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import com.example.oria.ui.theme.OriaTheme
import com.example.oria.ui.navigation.NavigationGraph
import com.example.oria.viewModel.global.SplashViewModel

/**
 * Activity of the application
 *
 */
class MainActivity : ComponentActivity() {

    // To create splash view
    private val splashViewModel: SplashViewModel by viewModels()

    companion object {

        // Permissions to have
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        val PERMISSION_TO_HAVE = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )



    }

    /**
     * Override of the function onCreate
     *
     * @param savedInstanceState
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this.hasRequiredPermission(PERMISSION_TO_HAVE)) {
            DEBUG(TagDebug.PERMISSIONS, "Request permissions")
            ActivityCompat.requestPermissions(this, PERMISSION_TO_HAVE, 0)

        }

        DEBUG(TagDebug.CHANGE_VIEW, "Splash view")
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.loading.value
            }
        }
        setContent {
            OriaTheme {
                DEBUG(TagDebug.CHANGE_VIEW, "Launch NavigationGraph")
                NavigationGraph(this)
            }
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}