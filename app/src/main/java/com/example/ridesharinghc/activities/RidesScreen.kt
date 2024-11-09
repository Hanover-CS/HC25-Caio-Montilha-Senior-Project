package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
 * [RidesScreen] activity displays the user's ride requests, ride offers, and ride history.
 * Users can view each type of ride in separate sections, organized by requests, offers, and history.
 * Each ride item can be viewed individually, and historical rides are permanently stored.
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
 * Composable function [RidesScreenContent] displays the main layout of the RidesScreen,
 * including sections for ride requests, ride offers, and ride history.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun RidesScreenContent(onBackClick: () -> Unit) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    var userRequests by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var userOffers by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var rideHistory by remember { mutableStateOf(listOf<Map<String, String>>()) }
    val db = FirebaseFirestore.getInstance()

    // Fetch user-specific ride requests
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            val requestRef = db.collection("rideRequests")
            requestRef.whereEqualTo("userId", userId).get()
                .addOnSuccessListener { snapshot ->
                    val requestsList = snapshot.documents.mapNotNull { it.data?.mapValues { entry -> entry.value.toString() } }
                    userRequests = requestsList

                    // Add requests to ride history
                    requestsList.forEach { request ->
                        addToRideHistory(request, db)
                    }
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

                    // Add offers to ride history
                    offersList.forEach { offer ->
                        addToRideHistory(offer, db)
                    }
                }
        }
    }

    // Fetch ride history
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            val historyRef = db.collection("rideHistory").whereEqualTo("userId", userId)
            historyRef.get().addOnSuccessListener { snapshot ->
                val historyList = snapshot.documents.mapNotNull { it.data?.mapValues { entry -> entry.value.toString() } }
                rideHistory = historyList
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

        // Separate sections for ride requests, offers, and history
        RideRequestsSection(userRequests)
        RideOffersSection(userOffers)
        RideHistorySection(rideHistory)
    }
}

/**
 * Composable function [RideRequestsSection] displays a list of the user's ride requests.
 * Each request is displayed as an individual [RideItem].
 *
 * @param userRequests List of maps containing ride request details.
 */
@Composable
fun RideRequestsSection(userRequests: List<Map<String, String>>) {
    Text(
        text = "Your Ride Requests:",
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )

    if (userRequests.isEmpty()) {
        Text(
            text = "No ride requests found.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        userRequests.forEach { request ->
            RideItem(request = request)
        }
    }

    Spacer(modifier = Modifier.height(32.dp))
}

/**
 * Composable function [RideOffersSection] displays a list of the user's ride offers.
 * Each offer is displayed as an individual [RideItem].
 *
 * @param userOffers List of maps containing ride offer details.
 */
@Composable
fun RideOffersSection(userOffers: List<Map<String, String>>) {
    Text(
        text = "Your Ride Offers:",
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )

    if (userOffers.isEmpty()) {
        Text(
            text = "No ride offers found.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        userOffers.forEach { offer ->
            RideItem(request = offer)
        }
    }

    Spacer(modifier = Modifier.height(32.dp))
}

/**
 * Composable function [RideHistorySection] displays a list of all past rides (requests and offers)
 * made by the user. These rides are stored permanently and are displayed as historical records.
 *
 * @param rideHistory List of maps containing all historical ride details.
 */
@Composable
fun RideHistorySection(rideHistory: List<Map<String, String>>) {
    Text(
        text = "Ride History:",
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )

    if (rideHistory.isEmpty()) {
        Text(
            text = "No rides in history.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        rideHistory.forEach { ride ->
            RideItem(request = ride, completed = true)
        }
    }
}

/**
 * Composable function [RideItem] displays a single ride item.
 * The ride item includes location and time details, with an optional completed status.
 *
 * @param request Map containing ride details.
 * @param completed Boolean indicating if the ride is completed (default is false).
 */
@Composable
fun RideItem(request: Map<String, String>, completed: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
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
 * Adds a ride request or offer to the ride history collection in Firestore.
 *
 * @param ride Map containing the ride data.
 * @param db Firebase Firestore instance.
 */
fun addToRideHistory(ride: Map<String, String>, db: FirebaseFirestore) {
    db.collection("rideHistory").add(ride)
}
