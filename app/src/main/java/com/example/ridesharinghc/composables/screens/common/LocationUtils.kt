package com.example.ridesharinghc.composables.screens.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ridesharinghc.data.constants.ErrorMessages.UNABLE_TO_GET_LOCATION
import com.example.ridesharinghc.data.constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState

/**
 * Function [checkLocationPermissionAndSetLocation] checks if location permission is granted.
 * If permission is granted, it retrieves the user's current location and updates the
 * [markerState] and [cameraPositionState] to reflect this position on the map.
 * If permission is not granted, it requests location permission from the user.
 *
 * @param context Context used for permission checks and displaying location.
 * @param cameraPositionState State to control the camera position on the map.
 * @param markerState Mutable state to hold the current location's marker position.
 */
fun checkLocationPermissionAndSetLocation(
    context: Context,
    cameraPositionState: CameraPositionState,
    markerState: MutableState<MarkerState>
) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    } else {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLocation = LatLng(it.latitude, it.longitude)
                markerState.value = MarkerState(currentLocation)
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
            } ?: run {
                Toast.makeText(context, constants.ErrorMessages.UNABLE_TO_GET_LOCATION, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
