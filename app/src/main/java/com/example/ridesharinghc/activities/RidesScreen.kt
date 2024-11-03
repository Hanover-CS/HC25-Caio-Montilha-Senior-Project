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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * [RidesScreen] activity displays a list of the user's ride requests, ride offers,
 * and completed rides. Users can view and mark rides as completed, and these
 * completed rides are saved to Firebase Firestore.
 */
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

/**
 * Composable function [RidesScreenContent] displays the user's ride requests, offers,
 * and completed rides in separate sections. Each section allows users to view their rides,
 * and requests or offers can be marked as completed.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun RidesScreenContent(onBackClick: () -> Unit) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    var userRequests by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var userOffers by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var completedRides by remember { mutableStateOf(listOf<Map<String, String>>()) }
    val db = FirebaseFirestore.getInstance()

    // Fetch user-specific ride requests
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            val requestRef = db.collection("rideRequests")
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
            val offerRef = db.collection("rideOffers")
            offerRef.whereEqualTo("userId", userId).get()
                .addOnSuccessListener { snapshot ->
                    val offersList = snapshot.documents.mapNotNull { it.data?.mapValues { entry -> entry.value.toString() } }
                    userOffers = offersList
                }
        }
    }

    // Fetch completed rides
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            val completedRef = db.collection("completedRides").whereEqualTo("userId", userId)
            completedRef.get().addOnSuccessListener { snapshot ->
                val completedList = snapshot.documents.mapNotNull { it.data?.mapValues { entry -> entry.value.toString() } }
                completedRides = completedList
            }
        }
    }

    // Main layout with sections for ride requests, offers, and completed rides
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
                    markRideAsCompleted(it, db, isRequest = true)
                    userRequests = userRequests - it
                    completedRides = completedRides + it
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
                    markRideAsCompleted(it, db, isRequest = false)
                    userOffers = userOffers - it
                    completedRides = completedRides + it
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

/**
 * Composable function [RideItem] displays a single ride item.
 * Allows marking the ride as completed and displays a confirmation dialog
 * when attempting to mark it as completed.
 *
 * @param request Map containing ride details.
 * @param completed Boolean indicating if the ride is completed (default is false).
 * @param onCompleteRide Lambda function to mark the ride as completed.
 */
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

/**
 * Marks the specified ride as completed and removes it from the active requests or offers
 * collection in Firestore. Moves the ride data to the "completedRides" collection.
 *
 * @param request Map containing the ride data.
 * @param db Firebase Firestore instance.
 * @param isRequest Boolean indicating if the ride is a request (true) or an offer (false).
 */
fun markRideAsCompleted(request: Map<String, String>, db: FirebaseFirestore, isRequest: Boolean) {
    val completedRef = db.collection("completedRides").document()
    completedRef.set(request)
        .addOnSuccessListener {
            // Successfully added to completed rides
            val collectionName = if (isRequest) "rideRequests" else "rideOffers"
            request["id"]?.let { id ->
                db.collection(collectionName).document(id).delete()
            }
        }
        .addOnFailureListener {
            // Handle failure to mark as completed
        }
}
