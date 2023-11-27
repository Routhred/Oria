package com.example.oria.ui.view.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.theme.loginFontFamily
import com.example.oria.ui.view.parameters.button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportTripPage(navController: NavController){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                contentAlignment = Alignment.Center,

                ) {
            Text(
                text = "Import an existing trip",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = loginFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                ),
            )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(size = 88.dp),
                    ),
            ) {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .offset(x = 0.dp, y = 100.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(23.dp, Alignment.Top),
                    ) {
                        var codeTrip by remember {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = codeTrip,
                            label = { Text(text = "Code") },
                            onValueChange = { text -> codeTrip = text },
                            modifier = Modifier
                                .width(320.dp)
                                .height(73.dp),
                            singleLine = true,
                        )
                        val screen = rememberInfoScreen()
                        button(
                            screen = screen,
                            navController = navController,
                            text = "Import",
                            height = 2
                        )
                        button(
                            screen = screen,
                            navController = navController,
                            text = "Create a new trip",
                            height = 2,
                            onClick = {navController.navigate("createTrip")}
                        )
                        button(
                            screen = screen,
                            navController = navController,
                            text = "Cancel",
                            height = 2,
                            color = MaterialTheme.colorScheme.error,
                            onClick = {navController.popBackStack()}
                        )


                    }
                }
            }
        }
    }
}