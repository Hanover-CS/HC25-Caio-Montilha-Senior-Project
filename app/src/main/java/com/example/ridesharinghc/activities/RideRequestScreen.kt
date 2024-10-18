package com.example.ridesharinghc.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import android.app.Activity
import android.content.pm.PackageItemInfo
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.google.api.Context
import com.google.firebase.auth.FirebaseAuth

class RideRequestScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Places API
        if (!Places.isInitialized()) {
            val applicationInfo = applicationContext.packageManager.getApplicationInfo(
                applicationContext.packageName,
                PackageManager.GET_META_DATA
            )
            Places.initialize(applicationContext, applicationInfo.metaData.getString("com.google.android.geo.API_KEY"))
        }

        setContent {
            RideSharingHCTheme {
                RideRequestScreenContent(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    val dropOffLocation = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val markerState = remember { mutableStateOf(MarkerState(LatLng(-33.852, 151.211))) }
    val cameraPositionState = rememberCameraPositionState()

    // Request location permission if not granted
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
            // Get the user's current location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLocation = LatLng(it.latitude, it.longitude)
                    markerState.value = MarkerState(currentLocation)
                    cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(currentLocation, 10f)
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
        // Back button and profile icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(painterResource(id = R.drawable.arrow), contentDescription = "Back", modifier = Modifier.size(32.dp))
            }
        }

        // Search bar for selecting drop-off location
        SearchLocationBar { latLng ->
            // Update map and marker state with the new selected location
            markerState.value = MarkerState(latLng)
            cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(latLng, 12f)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .border(2.dp, Color.Blue, RoundedCornerShape(8.dp))
        ) {
            // Google Map
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

        Spacer(modifier = Modifier.height(32.dp))  // space between map and form

        // Drop-off Location Field
        TextField(
            value = dropOffLocation.value,
            onValueChange = { dropOffLocation.value = it },
            label = { Text("Drop-off Location") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Date Field (formatted MM/DD/YYYY)
        TextField(
            value = date.value,
            onValueChange = {
                // Allow only numbers and auto-add slashes for MM/DD/YYYY format
                if (it.length <= 10) {
                    val formatted = it
                        .replace(Regex("[^0-9]"), "")
                        .chunked(2)
                        .joinToString("/")
                    date.value = formatted
                }
            },
            label = { Text("Date (MM/DD/YYYY)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Time Field
        TextField(
            value = time.value,
            onValueChange = { time.value = it },
            label = { Text("Select time") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Notes Field
        TextField(
            value = notes.value,
            onValueChange = { notes.value = it },
            label = { Text("Add notes") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Request Ride Button
        Button(
            onClick = {
                val request = hashMapOf(
                    "dropOffLocation" to dropOffLocation.value,
                    "date" to date.value,
                    "time" to time.value,
                    "notes" to notes.value
                )

                // Submit the request to Firebase Realtime Database
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                FirebaseDatabase.getInstance().reference.child("rideRequests").child(userId)
                    .push()
                    .setValue(request)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Request submitted", Toast.LENGTH_SHORT).show()
                        onBackClick()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to submit request", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Request Ride", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun SearchLocationBar(
    onLocationSelected: (LatLng) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val place = Autocomplete.getPlaceFromIntent(data!!)
            place.latLng?.let { onLocationSelected(it) }
        }
    }

    Button(onClick = {
        // Set up the autocomplete intent
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(context)
        launcher.launch(intent)
    }, modifier = Modifier.fillMaxWidth()) {
        Text("Search Drop-off Location")
    }
}
