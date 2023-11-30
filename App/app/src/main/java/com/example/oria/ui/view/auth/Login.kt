package com.example.oria.ui.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.oria.MainActivity
import com.example.oria.backend.ext.hasRequiredPermission
import com.example.oria.backend.server.HttpService
import com.example.oria.backend.server.HttpRoutes
import com.example.oria.permission.PermissionDialog
import com.example.oria.ui.theme.loginFontFamily
import io.ktor.client.HttpClient
import io.ktor.client.features.get
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "pLogin")
@Composable
fun LoginPage(navController: NavController = rememberNavController()) {
    if (!LocalContext.current.hasRequiredPermission(MainActivity.PERMISSION_TO_HAVE)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiary
                )
        ){
            PermissionDialog(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(size = 22.dp)
                    )
            )
        }

    }else {
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                            var name by remember {
                                mutableStateOf("")
                            }
                            var password by remember {
                                mutableStateOf("")
                            }
                            var errorField by remember {
                                mutableStateOf("")
                            }
                            var errorCode by remember {
                                mutableIntStateOf(0)
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
                                value = password,
                                label = { Text(text = "Password") },
                                onValueChange = { value -> password = value },
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
                                    .clickable {
                                        errorCode = TryLogin(
                                            name = name,
                                            password =
                                            password,
                                        )
                                        when (errorCode) {
                                            0 -> navController.navigate(
                                                "main",
                                            ) {
                                                popUpTo("auth") {
                                                    inclusive = true
                                                }
                                            }

                                            1 -> errorField = "Error Login"
                                        }
                                    },
                            ) {
                                Text(
                                    text = "Login",
                                    fontSize = 16.sp,

                                    fontFamily = loginFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,

                                    )
                            }

                            Row() {
                                Text(
                                    text = "Not register yet? Click",
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
                                        navController.navigate("register")
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
    }
}

fun TryLogin(name: String, password: String): Int {
    val client = HttpClient()

    return 0
}
