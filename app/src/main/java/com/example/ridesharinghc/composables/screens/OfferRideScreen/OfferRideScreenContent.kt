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
import com.example.ridesharinghc.composables.screens.common.BackButton
import com.example.ridesharinghc.composables.screens.common.MapSection
import com.example.ridesharinghc.composables.screens.OfferRideScreen.OfferSubmitButton
import com.example.ridesharinghc.composables.screens.OfferRideScreen.OfferInputFields

/**
 * Composable function [OfferRideScreenContent] displays the main screen content for offering a ride.
 * It includes location search functionality, a map view, and input fields for ride details.
 *
 * @param onBackClick Lambda function to handle the back button action, allowing navigation back.
 */
@Composable
fun OfferRideScreenContent(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val pickupLocation = remember { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState()
    val markerState = remember { mutableStateOf(MarkerState(LatLng(-33.852, 151.211))) }

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
            dropOffLocation = pickupLocation,
            onLocationSelected = { latLng, address ->
                markerState.value = MarkerState(latLng)
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                pickupLocation.value = address
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        MapSection(markerState = markerState, cameraPositionState = cameraPositionState)
        OfferInputFields(pickupLocation)
        OfferSubmitButton(
            onBackClick = onBackClick,
            pickupLocation = pickupLocation.value
        )
    }
}
