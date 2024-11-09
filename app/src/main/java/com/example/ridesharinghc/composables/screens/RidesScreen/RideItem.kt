package com.example.ridesharinghc.composables.screens.RidesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
