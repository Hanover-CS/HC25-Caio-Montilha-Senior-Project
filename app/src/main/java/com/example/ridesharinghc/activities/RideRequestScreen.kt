package com.example.ridesharinghc.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.core.content.ContextCompat
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.ridesharinghc.components.SearchLocationBar
import com.google.android.libraries.places.api.Places

class RideRequestScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                RideRequestScreenContent(onBackClick = { finish() })
            }
        }
    }
}

/**
 * Composable function [RideRequestScreenContent] displays the UI layout for the ride request screen.
 * It combines various sections such as the back button, location search bar, map, input fields, and submit button.
 *
 * @param onBackClick Lambda function to handle back button action.
 */
@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(onBackClick)
        LocationSearchBar()
        MapSection()
        InputFieldsSection()
        SubmitButton(onBackClick)
    }
}

/**
 * Composable function [BackButton] displays a button to navigate back to the previous screen.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun BackButton(onBackClick: () -> Unit) {
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
}

/**
 * Composable function [LocationSearchBar] displays a search bar allowing users to select a drop-off location.
 * It updates the selected location on the map upon user input.
 */
@Composable
fun LocationSearchBar() {
    val context = LocalContext.current
    val dropOffLocation = remember { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState()
    val markerState = remember { mutableStateOf(MarkerState(LatLng(-33.852, 151.211))) }

    SearchLocationBar { latLng, address ->
        dropOffLocation.value = address
        markerState.value = MarkerState(latLng)
        cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(latLng, 12f)
    }
}

/**
 * Composable function [MapSection] displays a Google Map with a marker for the selected drop-off location.
 * The marker position is updated based on the user-selected location.
 */
@Composable
fun MapSection() {
    val markerState = remember { mutableStateOf(MarkerState(LatLng(-33.852, 151.211))) }
    val cameraPositionState = rememberCameraPositionState()

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

/**
 * Composable function [InputFieldsSection] displays input fields for ride request details, including drop-off location,
 * date, time, and additional notes. It allows users to input details for their ride request.
 */
@Composable
fun InputFieldsSection() {
    val dropOffLocation = remember { mutableStateOf("") }
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
        onValueChange = {
            if (it.length <= 10) {
                val formatted = it.replace(Regex("[^0-9]"), "").chunked(2).joinToString("/")
                date.value = formatted
            }
        },
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

/**
 * Composable function [SubmitButton] provides a button to submit the ride request.
 * On clicking, it saves the request details to Firebase Firestore and navigates back upon success.
 *
 * @param onBackClick Lambda function to handle back navigation after submission.
 */
@Composable
fun SubmitButton(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val dropOffLocation = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    Button(
        onClick = {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val request = hashMapOf(
                "dropOffLocation" to dropOffLocation.value,
                "date" to date.value,
                "time" to time.value,
                "notes" to notes.value,
                "userId" to userId
            )

            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("rideRequests")
                .add(request)
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
