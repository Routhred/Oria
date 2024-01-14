package com.example.oria.ui.view.auth

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.oria.MainActivity
import com.example.oria.backend.data.storage.dataStore.PreferencesKey
import com.example.oria.backend.ext.hasRequiredPermission
import com.example.oria.backend.utils.DEBUG
import com.example.oria.backend.utils.TagDebug
import com.example.oria.permission.PermissionDialog
import com.example.oria.ui.theme.ERROR_LOGIN
import com.example.oria.ui.theme.ERROR_LOGIN_EMAIL
import com.example.oria.ui.theme.ERROR_LOGIN_ID
import com.example.oria.ui.theme.NO_ERROR
import com.example.oria.ui.theme.NO_RESPONSE
import com.example.oria.ui.theme.loginFontFamily
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.auth.LoginViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "pLogin")
@Composable
fun LoginPage(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.factory),
) {
    // TODO Import all user data when login

    if (false /*!LocalContext.current.hasRequiredPermission(MainActivity.PERMISSION_TO_HAVE)*/) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiary
                )
        ) {
            PermissionDialog(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(size = 22.dp)
                    )
            )
        }

    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    when(viewModel.loginUiState.error_code){
                        NO_RESPONSE -> MaterialTheme.colorScheme.background
                        else -> MaterialTheme.colorScheme.error
                    }
                ),
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .background(
                            when(viewModel.loginUiState.error_code){
                                NO_RESPONSE -> MaterialTheme.colorScheme.background
                                else -> MaterialTheme.colorScheme.error
                            }
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    contentAlignment = Alignment.Center,

                    ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        var errorField = viewModel.loginUiState.error_field
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
                        Text(
                            text = errorField,
                            style = TextStyle(
                                fontSize = 16.sp,
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
                            OutlinedTextField(
                                value = viewModel.loginUiState.username,
                                label = { Text(text = "Name") },
                                onValueChange = { text ->
                                    viewModel.updateUiState(
                                        viewModel.loginUiState.copy(
                                            username = text
                                        )
                                    )

                                },
                                modifier = Modifier
                                    .width(320.dp)
                                    .height(73.dp),
                                singleLine = true,
                            )
                            OutlinedTextField(
                                value = viewModel.loginUiState.password,
                                label = { Text(text = "Password") },
                                onValueChange = { text ->
                                    viewModel.updateUiState(
                                        viewModel.loginUiState.copy(
                                            password = text
                                        )
                                    )

                                },
                                modifier = Modifier
                                    .width(320.dp)
                                    .height(73.dp),
                                singleLine = true,
                            )
                            val context = LocalContext.current
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .height(56.dp)
                                    .width(150.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        shape = RoundedCornerShape(size = 8.dp),
                                    )
                                    .clickable {viewModel.login(context, navController)},
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

                            Row {
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
                            Row {
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

