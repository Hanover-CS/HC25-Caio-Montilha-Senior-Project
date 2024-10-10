package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue

/**
 * RideRequestScreen allows users to input ride details like drop-off location,
 * date, time, and additional notes. It includes a map placeholder for future
 * Google Maps integration and a request button to submit the ride request.
 */
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

@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    val dropOffLocation = remember { mutableStateOf(TextFieldValue()) }
    val date = remember { mutableStateOf(TextFieldValue()) }
    val time = remember { mutableStateOf(TextFieldValue()) }
    val notes = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .testTag("rideRequestScreen")
    ) {
        // Top Row for Back Arrow and Profile Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { /* TODO: Handle profile click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Placeholder for the map
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(2.dp, Color.Blue, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            // Empty Box to hold future Google Maps implementation
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Drop-off Location Field
        TextField(
            value = dropOffLocation.value,
            onValueChange = { dropOffLocation.value = it },
            label = { Text("Drop-off Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Date Field
        TextField(
            value = date.value,
            onValueChange = { date.value = it },
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Time Field
        TextField(
            value = time.value,
            onValueChange = { time.value = it },
            label = { Text("Select time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Add Notes Field
        TextField(
            value = notes.value,
            onValueChange = { notes.value = it },
            label = { Text("Add notes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Request Ride Button
        Button(
            onClick = { /* TODO: Handle ride request submission */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Request Ride", color = Color.White, fontSize = 16.sp)
        }
    }
}
