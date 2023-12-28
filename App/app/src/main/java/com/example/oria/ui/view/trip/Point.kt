package com.example.oria.ui.view.trip

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.oria.R
import com.example.oria.backend.location.Map
import com.example.oria.ui.navigation.rememberInfoScreen
import com.example.oria.ui.theme.loginFontFamily
import com.example.oria.ui.view.settings.button
import com.example.oria.viewModel.AppViewModelProvider
import com.example.oria.viewModel.trip.PointViewModel
import com.example.oria.viewModel.trip.TripViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.File
import java.io.FileInputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointPage(
    navController: NavController,
    pointViewModel: PointViewModel = viewModel(factory = AppViewModelProvider.PointFactory)
){
    Log.d("Open Page", "PointPage")
    val screen = rememberInfoScreen()
    val pointUiState by pointViewModel.pointUiState.collectAsState()
    val point = pointUiState.currentPoint
    val context = LocalContext.current
    val image = loadImageFromInternalStorage(context, point.image)
    if(image != null) {
        Image(
            image.asImageBitmap(),
            contentDescription = "Profile picture",
            modifier = Modifier
                .fillMaxWidth()
        )
    }else{
        Text(text = "No Image Found")
    }
    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                false,
                initialValue = SheetValue.Expanded
            )
        ),
        sheetPeekHeight = 50.dp,
        sheetContent = {
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
            Text(
                text = point.name,
                fontSize = 16.sp,
                fontFamily = loginFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = point.description
            )
            var tmpLocation = point.location
            if(tmpLocation.isEmpty()){
                tmpLocation = "0.00;0.00"
            }
            val latlong = tmpLocation.split(";")
            Log.d("Split location", "(${tmpLocation})")
            Map(
                lat = latlong[0].toDouble(),
                lng = latlong[1].toDouble(),
                modifier = Modifier
                    .height(screen.getDpHeight(6))
                    .width(screen.getDpWidth(7))
            )
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

fun loadImageFromInternalStorage(context: Context, imagePath: String): Bitmap? {
    try {
        val file = File(imagePath)
        val stream = FileInputStream(file)
        return BitmapFactory.decodeStream(stream)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}