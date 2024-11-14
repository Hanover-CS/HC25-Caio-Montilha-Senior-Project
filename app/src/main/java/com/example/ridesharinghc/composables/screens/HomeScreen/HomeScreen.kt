package com.example.ridesharinghc.composables.screens.HomeScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.ridesharinghc.firebase.FirebaseAuthHelper
import com.example.ridesharinghc.composables.screens.HomeScreen.HomeScreenTopBar
import com.example.ridesharinghc.composables.screens.HomeScreen.HomeScreenContent
import com.example.ridesharinghc.composables.screens.HomeScreen.ConfirmationDialog
import com.example.ridesharinghc.composables.screens.HomeScreen.handleAcceptRequest
import com.example.ridesharinghc.firebase.FirebaseAuthHelper.deleteRequest

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
