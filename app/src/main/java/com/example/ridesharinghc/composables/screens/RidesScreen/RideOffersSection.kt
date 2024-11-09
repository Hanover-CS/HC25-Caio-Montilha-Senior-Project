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
