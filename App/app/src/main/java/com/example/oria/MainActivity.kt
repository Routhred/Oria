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
import com.example.oria.backend.data.remote.dto.PostResponse
import com.example.oria.backend.ext.hasRequiredPermission
import com.example.oria.backend.server.HttpRoutes
import com.example.oria.ui.theme.OriaTheme
import com.example.oria.ui.navigation.NavigationGraph
import com.example.oria.viewModel.SplashViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()


    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        val PERMISSION_TO_HAVE = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this.hasRequiredPermission(PERMISSION_TO_HAVE)) {
            ActivityCompat.requestPermissions(this, PERMISSION_TO_HAVE, 0)

        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.loading.value
            }
        }
        setContent {
            OriaTheme {
                runBlocking{
                    val client = HttpClient()
                    val response: HttpResponse = client.get {
                        url {
                            protocol = URLProtocol.HTTP
                            host = HttpRoutes.HOST
                            path(HttpRoutes.LOGIN)
                            parameters.append("name", "toto")
                            parameters.append("password", "password")
                        }
                    }
                    println(response.content)
                    client.close()
                }
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