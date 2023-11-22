package com.example.oria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.oria.ui.theme.OriaTheme
import com.example.oria.ui.navigation.NavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OriaTheme {
                NavigationGraph(this)
            }
        }
    }
}
