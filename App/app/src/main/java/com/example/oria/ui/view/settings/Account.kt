package com.example.oria.ui.view.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oria.R
import com.example.oria.ui.navigation.rememberInfoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(navController: NavController) {
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
        Box {

            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(screen.getDpHeight(4))
                    .clip(CircleShape)
            )
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(screen.getDpHeight(4))
                    .clip(CircleShape)
            )
        }
        var name by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var surname by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = name,
            label = { Text(text = "Name") },
            onValueChange = { text -> name = text },
            modifier = Modifier
                .width(320.dp)
                .height(73.dp),
            singleLine = true,
        )
        OutlinedTextField(
            value = surname,
            label = { Text(text = "Password") },
            onValueChange = { value -> surname = value },
            modifier = Modifier
                .width(320.dp)
                .height(73.dp),
            singleLine = true,
        )
        OutlinedTextField(
            value = email,
            label = { Text(text = "Password") },
            onValueChange = { value -> email = value },
            modifier = Modifier
                .width(320.dp)
                .height(73.dp),
            singleLine = true,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(23.dp),
        ) {
            Button(
                modifier = Modifier
                    .width(screen.getDpWidth(3))
                    .height(screen.getDpHeight(2)),
                shape = RoundedCornerShape(size = 15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                onClick = {
                    println("Data saved")
                }
            ) {
                Text(text = "Save data")
            }
            Button(
                modifier = Modifier
                    .width(screen.getDpWidth(3))
                    .height(screen.getDpHeight(2)),
                shape = RoundedCornerShape(size = 15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                onClick = {
                    println("Go change password")
                }
            ) {
                Text(text = "Change password")
            }
        }
        Button(
            modifier = Modifier
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(2)),
            shape = RoundedCornerShape(size = 15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            onClick = {
                println("Delete account")
                navController.navigate("auth")
            }
        ) {
            Text(text = "Delete account")
        }

    }
}