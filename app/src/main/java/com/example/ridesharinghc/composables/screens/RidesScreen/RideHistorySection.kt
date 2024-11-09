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
