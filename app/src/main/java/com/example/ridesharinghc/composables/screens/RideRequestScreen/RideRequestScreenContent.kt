package com.example.ridesharinghc.composables.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ridesharinghc.R
import com.example.ridesharinghc.activities.RideRequestScreen
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.ridesharinghc.composables.screens.common.LocationSearchBar
import com.google.maps.android.compose.CameraPositionState
import com.example.ridesharinghc.composables.screens.common.checkLocationPermissionAndSetLocation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.ridesharinghc.composables.screens.common.MapSection
import com.example.ridesharinghc.composables.screens.common.BackButton
import com.google.android.libraries.places.api.Places

@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val dropOffLocation = remember { mutableStateOf("") }
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
        RequestInputFields(dropOffLocation)
        SubmitButton(
            onBackClick = onBackClick,
            dropOffLocation = dropOffLocation.value
        )
    }
}

@Composable
fun RequestInputFields(dropOffLocation: MutableState<String>) {
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    TextField(
        value = dropOffLocation.value,
        onValueChange = { dropOffLocation.value = it },
        label = { Text("Drop-off Location") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
    TextField(
        value = date.value,
        onValueChange = { date.value = it },
        label = { Text("Date (MM/DD/YYYY)") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    TextField(
        value = time.value,
        onValueChange = { time.value = it },
        label = { Text("Select time") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
    TextField(
        value = notes.value,
        onValueChange = { notes.value = it },
        label = { Text("Add notes") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
}

@Composable
fun SubmitButton(
    onBackClick: () -> Unit,
    dropOffLocation: String
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val request = hashMapOf(
                "dropOffLocation" to dropOffLocation,
                "userId" to userId
            )

            FirebaseFirestore.getInstance().collection("rideRequests")
                .add(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Ride requested successfully", Toast.LENGTH_SHORT).show()
                    onBackClick()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to request ride", Toast.LENGTH_SHORT).show()
                }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text("Request Ride", color = Color.White, fontSize = 16.sp)
    }
}


