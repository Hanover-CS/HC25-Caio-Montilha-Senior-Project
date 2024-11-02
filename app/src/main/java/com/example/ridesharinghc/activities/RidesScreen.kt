package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RidesScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                RidesScreenContent(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun RidesScreenContent(onBackClick: () -> Unit) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    var userRequests by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var userOffers by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var completedRides by remember { mutableStateOf(listOf<Map<String, String>>()) }

    // Fetch user-specific ride requests
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            val requestRef = FirebaseFirestore.getInstance().collection("rideRequests")
            requestRef.whereEqualTo("userId", userId).get()
                .addOnSuccessListener { snapshot ->
                    val requestsList = snapshot.documents.mapNotNull { it.data?.mapValues { entry -> entry.value.toString() } }
                    userRequests = requestsList
                }
        }
    }

    // Fetch user-specific ride offers
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            val offerRef = FirebaseFirestore.getInstance().collection("rideOffers")
            offerRef.whereEqualTo("userId", userId).get()
                .addOnSuccessListener { snapshot ->
                    val offersList = snapshot.documents.mapNotNull { it.data?.mapValues { entry -> entry.value.toString() } }
                    userOffers = offersList
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back arrow to return to the previous screen
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Screen Title
        Text(
            text = "My Rides",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display User's Ride Requests
        Text(
            text = "Your Ride Requests:",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        if (userRequests.isEmpty()) {
            Text(
                text = "No ride requests found.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start).padding(vertical = 8.dp)
            )
        } else {
            userRequests.forEach { request ->
                RideItem(request = request, onCompleteRide = {
                    completedRides = completedRides + it
                    userRequests = userRequests - it
                })
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Display User's Ride Offers
        Text(
            text = "Your Ride Offers:",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        if (userOffers.isEmpty()) {
            Text(
                text = "No ride offers found.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start).padding(vertical = 8.dp)
            )
        } else {
            userOffers.forEach { offer ->
                RideItem(request = offer, onCompleteRide = {
                    completedRides = completedRides + it
                    userOffers = userOffers - it
                })
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Display Completed Rides
        Text(
            text = "Completed Rides:",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        if (completedRides.isEmpty()) {
            Text(
                text = "No completed rides.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start).padding(vertical = 8.dp)
            )
        } else {
            completedRides.forEach { ride ->
                RideItem(request = ride, completed = true)
            }
        }
    }
}

@Composable
fun RideItem(request: Map<String, String>, completed: Boolean = false, onCompleteRide: ((Map<String, String>) -> Unit)? = null) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog && !completed) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Complete Ride") },
            text = { Text("Are you sure you want to mark this ride as completed?") },
            confirmButton = {
                TextButton(onClick = {
                    onCompleteRide?.invoke(request)
                    showDialog = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .clickable(enabled = !completed) { showDialog = true },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Location: ${request["dropOffLocation"] ?: request["pickupLocation"]}", fontSize = 16.sp)
            Text(text = "Time: ${request["time"]}", fontSize = 16.sp)
            if (completed) {
                Text(
                    text = "Status: Completed",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
            }
        }
    }
}
