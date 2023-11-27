package com.example.oria.ui.view.trip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oria.R
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.theme.loginFontFamily
import com.example.oria.ui.view.parameters.button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointPage(navController: NavController){
    val screen = rememberInfoScreen()
    Image(
        painter = painterResource(R.drawable.logo_round),
        contentDescription = "Profile picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
    )
    BottomSheetScaffold(sheetContent = {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(screen.getDpHeight(15))
            .background(
                color = MaterialTheme.colorScheme.secondary
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                screen.getDpHeight(),
                Alignment.Top,
            ),
        ){
            var name by remember{
                mutableStateOf("Place")
            }
            var description by remember{
                mutableStateOf("Description")
            }
            Text(
                text = name,
                fontSize = 16.sp,
                fontFamily = loginFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = description
            )
            Box(
                modifier = Modifier
                    .height(screen.getDpHeight(6))
                    .width(screen.getDpWidth(7))
                    .background(color = MaterialTheme.colorScheme.tertiary)
            ){
                Text(text = "Set map here")
            }
            button(
                screen = screen,
                navController = navController,
                text = "Delete point",
                height = 2,
                color = MaterialTheme.colorScheme.error,
                onClick = {
                    // TODO delete point
                    navController.popBackStack()
                }
            )


        }


    },
    sheetContainerColor = MaterialTheme.colorScheme.secondary
    ) {


    }
}