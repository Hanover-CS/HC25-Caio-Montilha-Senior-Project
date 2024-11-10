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
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.ridesharinghc.components.SearchLocationBar

@Composable
fun OfferRideScreenContent(onBackClick: () -> Unit) {
    val driverName = remember { mutableStateOf("") }
    val pickupLocation = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val seatsAvailable = remember { mutableStateOf("") }
    val context = LocalContext.current

    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val markerState = remember { mutableStateOf(MarkerState(LatLng(-33.852, 151.211))) }
    val cameraPositionState = rememberCameraPositionState()

    // Check and request location permissions
    LaunchedEffect(key1 = Unit) {
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
                    Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
        }

        SearchLocationBar { latLng, address ->
            markerState.value = MarkerState(latLng)
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            pickupLocation.value = address
        }

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
        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = driverName.value,
            onValueChange = { driverName.value = it },
            label = { Text("Driver's name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        TextField(
            value = pickupLocation.value,
            onValueChange = { pickupLocation.value = it },
            label = { Text("Pickup and Drop-off Location") },
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
            label = { Text("Time") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val offer = hashMapOf(
                    "driverName" to driverName.value,
                    "pickupLocation" to pickupLocation.value,
                    "date" to date.value,
                    "time" to time.value,
                    "seatsAvailable" to seatsAvailable.value,
                    "userId" to userId
                )

                FirebaseFirestore.getInstance().collection("rideOffers")
                    .add(offer)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Ride offered successfully", Toast.LENGTH_SHORT).show()
                        onBackClick()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to offer ride", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Offer Ride", color = Color.White, fontSize = 16.sp)
        }
    }
}
