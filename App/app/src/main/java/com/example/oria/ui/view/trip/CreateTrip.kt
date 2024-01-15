package com.example.oria.ui.view.trip

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.theme.loginFontFamily
import com.example.oria.ui.view.settings.button
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.database.TripEntryViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripPage(
    navController: NavController,
    viewModel: TripEntryViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val screen = rememberInfoScreen()
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
                    .fillMaxHeight(0.15f),
                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = "Create a new trip",
                    style = TextStyle(
                        fontSize = 20.sp,
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
                        .offset(x = 0.dp, y = 70.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            screen.getDpHeight(0.5f),
                            Alignment.Top
                        ),
                    ) {
                        var day by remember {
                            mutableIntStateOf(1)
                        }
                        var month by remember {
                            mutableIntStateOf(1)
                        }
                        var year by remember {
                            mutableIntStateOf(2000)
                        }

                        OutlinedTextField(
                            value = viewModel.tripUiState.tripDetails.name,
                            label = { Text(text = "Name") },
                            onValueChange = {
                                viewModel.updateUiState(
                                    viewModel.tripUiState.tripDetails.copy(name = it)
                                )
                            },
                            modifier = Modifier
                                .width(320.dp)
                                .height(73.dp),
                            singleLine = true,
                        )
                        OutlinedTextField(
                            value = viewModel.tripUiState.tripDetails.description,
                            label = { Text(text = "Description") },
                            onValueChange = {
                                viewModel.updateUiState(
                                    viewModel.tripUiState.tripDetails.copy(description = it)
                                )
                            },
                            modifier = Modifier
                                .width(320.dp)
                                .height(73.dp),
                            singleLine = true,
                        )
                        OutlinedTextField(
                            value = viewModel.tripUiState.tripDetails.location,
                            label = { Text(text = "Place") },
                            onValueChange = {
                                viewModel.updateUiState(
                                    viewModel.tripUiState.tripDetails.copy(location = it)
                                )
                            },
                            modifier = Modifier
                                .width(320.dp)
                                .height(73.dp),
                            singleLine = true,
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                screen.getDpWidth(0.5f),
                                Alignment
                                    .Start
                            )
                        ) {
                            OutlinedTextField(
                                value = day.toString(),
                                label = { Text(text = "Day") },
                                onValueChange = { text ->
                                    day = if (text.toIntOrNull() == null) {
                                        0
                                    } else {
                                        text.toInt()
                                    }
                                },
                                modifier = Modifier
                                    .width(screen.getDpWidth(2))
                                    .height(screen.getDpWidth(2)),
                                singleLine = true,
                            )
                            OutlinedTextField(
                                value = month.toString(),
                                label = { Text(text = "Month") },
                                onValueChange = { text ->
                                    month =
                                        if (text.toIntOrNull() == null) {
                                            0
                                        } else {
                                            text.toInt()
                                        }
                                },
                                modifier = Modifier
                                    .width(screen.getDpWidth(2))
                                    .height(screen.getDpWidth(2)),
                                singleLine = true,
                            )
                            OutlinedTextField(
                                value = year.toString(),
                                label = { Text(text = "Year") },
                                onValueChange = { text ->
                                    year =
                                        if (text.toIntOrNull() == null) {
                                            0
                                        } else {
                                            text.toInt()
                                        }
                                },
                                modifier = Modifier
                                    .width(screen.getDpWidth(2))
                                    .height(screen.getDpWidth(2)),
                                singleLine = true,
                            )

                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                screen.getDpWidth(), Alignment
                                    .Start
                            )
                        ) {
                            button(
                                screen = screen,
                                navController = navController,
                                text = "Save",
                                height = 2,
                                width = 3,
                                enable = viewModel.tripUiState.isEntryValid,
                                onClick = {
                                    viewModel.updateUiState(
                                        viewModel.tripUiState.tripDetails.copy(date = "$year-$month-$day")
                                    )
                                    coroutineScope.launch {
                                        viewModel.saveItem()
                                        navController.popBackStack()
                                    }
                                }
                            )
                            button(
                                screen = screen,
                                navController = navController,
                                text = "Cancel",
                                height = 2,
                                width = 3,
                                color = MaterialTheme.colorScheme.error,
                                onClick = { navController.popBackStack() }
                            )
                        }
                        button(
                            screen = screen,
                            navController = navController,
                            text = "Import a trip",
                            height = 2,
                            onClick = { navController.navigate("importTrip") }
                        )


                    }
                }
            }
        }
    }
}
