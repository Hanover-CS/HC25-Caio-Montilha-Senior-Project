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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue

/**
 * OfferRideScreen displays the UI for drivers to offer rides.
 * The screen includes fields for entering driver details, time, location, and available seats.
 * Displays existing ride requests and a placeholder for map.
 * Provides a button to submit the offered ride.
 */
class OfferRideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                OfferRideScreenContent(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun OfferRideScreenContent(onBackClick: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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

        // Placeholder for the map (currently empty)
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

        // List of Existing Requests
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "List of Existing Requests:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                RequestItem("Walmart", "2:00 PM")
                RequestItem("TJ Maxx", "5:30 PM")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fields to Offer a Ride
        Text(
            text = "Set available time and location to offer the ride:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val driverName = remember { mutableStateOf(TextFieldValue()) }
        val pickupLocation = remember { mutableStateOf(TextFieldValue()) }
        val date = remember { mutableStateOf(TextFieldValue()) }
        val time = remember { mutableStateOf(TextFieldValue()) }
        val seatsAvailable = remember { mutableStateOf(TextFieldValue()) }

        TextField(
            value = driverName.value,
            onValueChange = { driverName.value = it },
            label = { Text("Driver's name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = pickupLocation.value,
            onValueChange = { pickupLocation.value = it },
            label = { Text("Pickup and Drop-off Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = date.value,
            onValueChange = { date.value = it },
            label = { Text("Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = time.value,
            onValueChange = { time.value = it },
            label = { Text("Time") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextField(
            value = seatsAvailable.value,
            onValueChange = { seatsAvailable.value = it },
            label = { Text("Seats available") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to Submit Offer
        Button(
            onClick = {
                // TODO: Handle offer ride submission logic here
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text("Offer Ride", color = Color.White, fontSize = 16.sp)
        }
    }
}
