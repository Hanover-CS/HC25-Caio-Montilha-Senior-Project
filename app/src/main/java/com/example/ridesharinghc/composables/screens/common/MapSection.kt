package com.example.ridesharinghc.composables.screens.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

/**
 * Composable function [MapSection] displays a Google Map with a marker at a specified location.
 * It includes a blue border and rounded corners to enhance its appearance.
 *
 * @param markerState Mutable state representing the marker's position on the map.
 * @param cameraPositionState Controls the camera position on the map, enabling map view adjustments.
 */
@Composable
fun MapSection(
    markerState: MutableState<MarkerState>,
    cameraPositionState: CameraPositionState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .border(2.dp, Color.Blue, RoundedCornerShape(8.dp))
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = markerState.value,
                title = "Selected Location"
            )
        }
    }
}
