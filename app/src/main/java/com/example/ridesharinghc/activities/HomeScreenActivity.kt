package com.example.ridesharinghc.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import java.util.UUID
import com.example.ridesharinghc.composables.screens.HomeScreen.ActionBox
import com.example.ridesharinghc.utils.deleteRequest
import com.example.ridesharinghc.utils.deleteOffer
import com.example.ridesharinghc.composables.screens.HomeScreen.handleAcceptRequest
import com.example.ridesharinghc.composables.screens.HomeScreen.ConfirmationDialog
import com.example.ridesharinghc.composables.screens.HomeScreen.RideOfferCard
import com.example.ridesharinghc.composables.screens.HomeScreen.RideRequestCard
import com.example.ridesharinghc.composables.screens.HomeScreen.HomeScreenActionRow

/**
 * [HomeScreenActivity] serves as the main screen of the RideSharingHC app.
 * It displays available ride requests and offers, and allows users to create
 * or delete ride offers and requests. Users can also access the menu and profile
 * screens from this activity. The UI is built using Jetpack Compose.
 */
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

/**
 * Composable function [HomeScreen] that displays the main content of the home screen.
 * It shows current ride requests and offers and allows users to accept or delete them.
 * It also includes navigation options to the Menu and Profile screens.
 */
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    var requests by remember { mutableStateOf(listOf<Pair<String, Map<String, String>>>()) }
    var offers by remember { mutableStateOf(listOf<Pair<String, Map<String, String>>>()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedRequest by remember { mutableStateOf<Pair<String, Map<String, String>>?>(null) }

    // Fetch ride requests
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

    // Fetch ride offers
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

    // Display confirmation dialog for accepting a request
    if (showDialog) {
        ConfirmationDialog(
            onConfirm = {
                selectedRequest?.let { (key, request) ->
                    handleAcceptRequest(context, request)
                    deleteRequest(key)
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    // UI elements for the home screen, including navigation and action buttons
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        HomeScreenTopBar(context)
        HomeScreenContent(
            context = context,
            requests = requests,
            offers = offers,
            currentUserId = currentUserId,
            showDialog = showDialog,
            setShowDialog = { showDialog = it },
            selectedRequest = selectedRequest,
            setSelectedRequest = { selectedRequest = it }
        )
    }
}

/**
 * Composable function [HomeScreenTopBar] that displays the top row of buttons for navigation.
 */
@Composable
fun HomeScreenTopBar(context: Context) {
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
}

/**
 * Composable function [HomeScreenContent] that shows the main content of the home screen,
 * including ride request and offer cards.
 */
@Composable
fun HomeScreenContent(
    context: Context,
    requests: List<Pair<String, Map<String, String>>>,
    offers: List<Pair<String, Map<String, String>>>,
    currentUserId: String?,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    selectedRequest: Pair<String, Map<String, String>>?,
    setSelectedRequest: (Pair<String, Map<String, String>>?) -> Unit
) {
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

        HomeScreenActionRow(context)

        RideRequestCard(
            requests = requests,
            currentUserId = currentUserId,
            setSelectedRequest = setSelectedRequest,
            setShowDialog = setShowDialog
        )

        RideOfferCard(
            offers = offers,
            currentUserId = currentUserId,
            setSelectedRequest = setSelectedRequest,
            setShowDialog = setShowDialog
        )
    }
}
