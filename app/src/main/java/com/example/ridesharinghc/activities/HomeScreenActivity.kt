package com.example.ridesharinghc.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    var requests by remember { mutableStateOf(listOf<Pair<String, Map<String, String>>>()) }
    var offers by remember { mutableStateOf(listOf<Pair<String, Map<String, String>>>()) }

    LaunchedEffect(Unit) {
        val requestRef = FirebaseFirestore.getInstance().collection("rideRequests")
        requestRef.addSnapshotListener { snapshot, _ ->
            val requestList = mutableListOf<Pair<String, Map<String, String>>>()
            snapshot?.documents?.forEach { document ->
                val request = document.data?.mapValues { it.value.toString() }
                request?.let { requestList.add(document.id to it) }
            }
            requests = requestList
        }
    }

    LaunchedEffect(Unit) {
        val offerRef = FirebaseFirestore.getInstance().collection("rideOffers")
        offerRef.addSnapshotListener { snapshot, _ ->
            val offerList = mutableListOf<Pair<String, Map<String, String>>>()
            snapshot?.documents?.forEach { document ->
                val offer = document.data?.mapValues { it.value.toString() }
                offer?.let { offerList.add(document.id to offer) }
            }
            offers = offerList
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val intent = Intent(context, MenuScreen::class.java)
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.menu_three_lines),
                    contentDescription = "Menu",
                    modifier = Modifier.size(42.dp)
                )
            }

            IconButton(onClick = {
                val intent = Intent(context, UserProfileScreen::class.java)
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Profile",
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 130.dp)
        ) {
            Text(
                text = "RideSharingHC",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ActionBox(
                    icon = painterResource(id = R.drawable.ic_get_ride),
                    text = "Get a Ride",
                    onClick = {
                        val intent = Intent(context, RideRequestScreen::class.java)
                        context.startActivity(intent)
                    },
                    tag = "getARideButton"
                )
                ActionBox(
                    icon = painterResource(id = R.drawable.ic_offer_ride),
                    text = "Offer a Ride",
                    onClick = {
                        val intent = Intent(context, OfferRideScreen::class.java)
                        context.startActivity(intent)
                    },
                    tag = "offerARideButton"
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Current Requests:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    requests.forEach { (key, request) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${request["dropOffLocation"]} - ${request["time"]}",
                                fontSize = 16.sp
                            )
                            Row {
                                if (request["userId"] == currentUserId) {
                                    // Show Delete button if the request was created by the current user
                                    IconButton(onClick = {
                                        deleteRequest(key)
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_delete),
                                            contentDescription = "Delete",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                } else {
                                    // Show Accept button for other users
                                    IconButton(onClick = {
                                        handleAcceptRequest(context, request)
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_check),
                                            contentDescription = "Accept",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Current Rides Offered:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    offers.forEach { (key, offer) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${offer["pickupLocation"]} - ${offer["time"]}",
                                fontSize = 16.sp
                            )
                            if (offer["userId"] == currentUserId) {
                                IconButton(onClick = {
                                    deleteOffer(key)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = "Delete",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun handleAcceptRequest(context: Context, request: Map<String, String>) {
    val intent = Intent(context, ContactUserScreen::class.java).apply {
        putExtra("userId", request["userId"]) // Passa o ID do usuário solicitante
    }
    context.startActivity(intent)
}

fun deleteRequest(requestId: String) {
    val requestRef = FirebaseFirestore.getInstance().collection("rideRequests").document(requestId)
    requestRef.delete()
}

fun deleteOffer(offerId: String) {
    val offerRef = FirebaseFirestore.getInstance().collection("rideOffers").document(offerId)
    offerRef.delete()
}

@Composable
fun ActionBox(
    icon: Painter,
    text: String,
    onClick: () -> Unit,
    tag: String
) {
    Column(
        modifier = Modifier
            .size(150.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable(onClick = onClick)
            .testTag(tag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 16.sp)
    }
}