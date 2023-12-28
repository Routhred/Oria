package com.example.oria.backend.location

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun Map(lat: Double, lng: Double, modifier: Modifier = Modifier, zoom: Float = 12.0f) {
    val co = LatLng(
        lat,
        lng
    )
    val cameraState = CameraPositionState(
        CameraPosition(
            co,
            zoom,
            1f,
            0f

        )
    )
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraState,
        properties = MapProperties(
            mapType = MapType.NORMAL
        )
    ) {
        Marker(
            state = MarkerState(
                position = co
            ),
            draggable = false
        )
    }
}
