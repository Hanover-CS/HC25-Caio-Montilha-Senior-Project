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
import com.example.ridesharinghc.composables.screens.RideRequestScreen.SubmitButton
import com.example.ridesharinghc.composables.screens.RideRequestScreen.InputFieldsSection
import com.example.ridesharinghc.composables.screens.RideRequestScreen.MapSection
import com.example.ridesharinghc.composables.screens.RideRequestScreen.LocationSearchBar

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
    val dropOffLocation = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(onBackClick)
        LocationSearchBar(dropOffLocation, date, time, notes)
        MapSection()
        InputFieldsSection(
            dropOffLocation = dropOffLocation,
            date = date,
            time = time,
            notes = notes
        )
        SubmitButton(
            onBackClick = onBackClick,
            dropOffLocation = dropOffLocation.value,
            date = date.value,
            time = time.value,
            notes = notes.value
        )
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
