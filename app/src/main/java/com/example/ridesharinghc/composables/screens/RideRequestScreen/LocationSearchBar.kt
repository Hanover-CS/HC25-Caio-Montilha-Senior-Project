package com.example.ridesharinghc.composables.screens.RideRequestScreen

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.ridesharinghc.components.SearchLocationBar
import com.google.android.gms.maps.CameraUpdateFactory

/**
 * Composable function [LocationSearchBar] displays a search bar allowing users to select a drop-off location.
 * It updates the selected location on the map upon user input.
 *
 * @param dropOffLocation MutableState for the drop-off location selected by the user.
 * @param date MutableState for the date of the ride request.
 * @param time MutableState for the time of the ride request.
 * @param notes MutableState for additional notes related to the ride request.
 */
@Composable
fun LocationSearchBar(
    dropOffLocation: MutableState<String>,
    date: MutableState<String>,
    time: MutableState<String>,
    notes: MutableState<String>
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val markerState = remember { mutableStateOf(MarkerState(LatLng(-33.852, 151.211))) }

    SearchLocationBar { latLng, address ->
        markerState.value = MarkerState(latLng)
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
        dropOffLocation.value = address
    }
}
