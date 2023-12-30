package com.example.oria.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.oria.backend.ext.getActivity
import com.example.oria.openAppSettings
import com.example.oria.ui.navigation.rememberInfoScreen


// TODO Modify permission system if time
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        content = {
            DialogContent()
        },
        modifier = modifier
    )
}

@Composable
fun DialogContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Permission required")
        Divider(modifier = Modifier.fillMaxWidth())
        Text(
            "You have to give us the required permissions." +
                    "If the popup doesn't appeard you have to go to the settings"
        )
        Divider(modifier = Modifier.fillMaxWidth())
        Row(
            horizontalArrangement = Arrangement.spacedBy(rememberInfoScreen().getDpWidth())
        ) {
            val activity = (LocalContext.current.getActivity())
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                onClick = {
                    activity?.openAppSettings()
                }
            ) {
                Text(text = "Grant permission")
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                onClick = {
                    activity?.finish()
                }) {
                Text("Exit")
            }
        }

    }
}

