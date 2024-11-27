package com.example.ridesharinghc.composables.screens.HomeScreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.ridesharinghc.data.constants.Labels.RIDE_SHARING_HC

/**
 * Composable function [HomeScreenContent] that shows the main content of the home screen,
 * including ride request and offer cards, with scrollable support.
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 130.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = RIDE_SHARING_HC,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
        item {
            HomeScreenActionRow(context)
        }
        item {
            RideRequestCard(
                requests = requests,
                currentUserId = currentUserId,
                setSelectedRequest = setSelectedRequest,
                setShowDialog = setShowDialog
            )
        }
        item {
            RideOfferCard(
                offers = offers,
                currentUserId = currentUserId,
                setSelectedRequest = setSelectedRequest,
                setShowDialog = setShowDialog
            )
        }
    }
}
