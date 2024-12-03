package com.example.ridesharinghc.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.ridesharinghc.composables.screens.common.LocationSearchBar
import com.example.ridesharinghc.composables.screens.common.checkLocationPermissionAndSetLocation
import com.example.ridesharinghc.composables.screens.common.MapSection
import com.example.ridesharinghc.composables.screens.common.BackButton
import com.example.ridesharinghc.composables.screens.RideRequestScreen.SubmitButton
import com.example.ridesharinghc.composables.screens.RideRequestScreen.RequestInputFields

/**
 * Composable function [RideRequestScreenContent] displays the main content for the Ride Request screen,
 * allowing users to enter details for a ride request, including drop-off location, date, time, and notes.
 * It includes a map view, location search bar, and back navigation.
 *
 * @param onBackClick Lambda function to handle the back button action, enabling navigation back.
 */
@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val dropOffLocation = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState()
    val markerState = remember { mutableStateOf(MarkerState(LatLng(0.0, 0.0))) }

    // Request location permission and initialize FusedLocationProviderClient
    LaunchedEffect(Unit) {
        checkLocationPermissionAndSetLocation(context, cameraPositionState, markerState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(onBackClick)
        // Use LocationSearchBar
        LocationSearchBar(
            dropOffLocation = dropOffLocation,
            onLocationSelected = { latLng, address ->
                markerState.value = MarkerState(latLng)
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                dropOffLocation.value = address
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        MapSection(markerState = markerState, cameraPositionState = cameraPositionState)
        Spacer(modifier = Modifier.height(16.dp))
        RequestInputFields(
            dropOffLocation = dropOffLocation,
            date = date,
            time = time,
            notes = notes
        )
        Spacer(modifier = Modifier.height(16.dp))
        SubmitButton(
            onBackClick = onBackClick,
            dropOffLocation = dropOffLocation.value,
            date = date.value,
            time = time.value
        )
    }
}
