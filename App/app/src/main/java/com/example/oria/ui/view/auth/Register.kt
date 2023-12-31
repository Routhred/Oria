package com.example.oria.ui.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.oria.ui.theme.*
import com.example.oria.ui.theme.loginFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavController) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                contentAlignment = Alignment.Center,

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "WELCOME TO",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        ),
                    )
                    Text(
                        text = "ORIA",
                        style = TextStyle(
                            fontSize = 70.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }
            }
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(88.dp, 88.dp, 0.dp, 0.dp),
                    ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(23.dp, Alignment.Top),
                    modifier = Modifier
                        .offset(x = 0.dp, y = 80.dp)
                        .fillMaxWidth(0.8f),
                ) {
                    var name by remember {
                        mutableStateOf("")
                    }
                    var surname by remember {
                        mutableStateOf("")
                    }
                    var email by remember {
                        mutableStateOf("")
                    }
                    var password by remember {
                        mutableStateOf("")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(23.dp),
                    ) {
                        OutlinedTextField(
                            value = name,
                            label = { Text(text = "Name") },
                            onValueChange = { text -> name = text },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.45f),
                        )
                        OutlinedTextField(
                            value = name,
                            label = { Text(text = "Surname") },
                            onValueChange = { text -> name = text },
                            singleLine = true,
                        )
                    }

                    OutlinedTextField(
                        value = name,
                        label = { Text(text = "Email") },
                        onValueChange = { text -> name = text },
                        modifier = Modifier
                            .width(320.dp)
                            .height(73.dp),
                        singleLine = true,
                    )
                    OutlinedTextField(
                        value = name,
                        label = { Text(text = "Password") },
                        onValueChange = { text -> name = text },
                        modifier = Modifier
                            .width(320.dp)
                            .height(73.dp),
                        singleLine = true,
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(56.dp)
                            .width(150.dp)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(size = 8.dp),
                            )
                            .clickable { println("Name : $name, password : $password") },
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 16.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Row() {
                        Text(
                            text = "Already registered? Click",
                            fontSize = 16.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Center,

                        )
                        Text(
                            text = " here",
                            fontSize = 16.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                navController.navigate("login")
                            },
                        )
                    }
                    Row() {
                        Text(
                            text = "Password forgotten? Click",
                            fontSize = 16.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            )
                        Text(
                            text = " here",
                            fontSize = 16.sp,
                            fontFamily = loginFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                navController.navigate("password")
                            },
                        )
                    }
                }
            }
        }
    }
}
