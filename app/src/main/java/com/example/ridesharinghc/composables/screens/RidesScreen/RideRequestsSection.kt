package com.example.ridesharinghc.composables.screens.RidesScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
