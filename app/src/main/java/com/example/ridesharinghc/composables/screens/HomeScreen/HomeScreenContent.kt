package com.example.ridesharinghc.composables.screens.HomeScreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.composables.screens.HomeScreen.HomeScreenActionRow
import com.example.ridesharinghc.composables.screens.HomeScreen.RideOfferCard
import com.example.ridesharinghc.composables.screens.HomeScreen.RideRequestCard
import androidx.compose.ui.Alignment

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