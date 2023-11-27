package com.example.oria.ui.view.trip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.oria.ui.view.parameters.button

@Composable
fun AddPointPage(navController: NavController){
    val screen = rememberInfoScreen()
    Column (
        verticalArrangement = Arrangement.spacedBy(screen.getDpHeight(0.5f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(size = 15.dp)
            )
            .fillMaxSize()
            .padding(vertical = screen.getDpHeight())
    ){
        Box(
            modifier = Modifier
                .clickable {
                    // TODO open camera
                }
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(5))
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(size = 15.dp))
        ){
            var isImage by remember{
                mutableStateOf(false)
            }
            if(isImage) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(screen.getDpHeight(4))
                        .clip(CircleShape)
                )
            }else{
                Text(text = "Click to take a photo")
            }
        }
        var name by remember {
            mutableStateOf("")
        }
        var description by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = name,
            label = { Text(text = "Name") },
            onValueChange = { text -> name = text },
            modifier = Modifier
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(1.5f)),
            singleLine = true,
        )
        OutlinedTextField(
            value = description,
            label = { Text(text = "Description") },
            onValueChange = { text -> description = text },
            modifier = Modifier
                .width(screen.getDpWidth(7))
                .height(screen.getDpHeight(4))
        )
        button(
            screen = screen,
            navController = navController,
            text = "Set GPS",
            height = 2,
            onClick = {
                // TODO GPS
            }
        )
        button(
            screen = screen,
            navController = navController,
            text = "Save",
            height = 2,
            onClick = {
                // TODO Save new Point
                navController.popBackStack()
            }
        )
        button(
            screen = screen,
            navController = navController,
            text = "Cancel",
            height = 2,
            onClick = {
                navController.popBackStack()
            },
            color = MaterialTheme.colorScheme.error
        )
    }
}