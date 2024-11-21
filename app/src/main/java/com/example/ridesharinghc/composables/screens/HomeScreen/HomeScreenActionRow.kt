package com.example.ridesharinghc.composables.screens.HomeScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.activities.OfferRideScreen
import com.example.ridesharinghc.activities.RideRequestScreen
import com.example.ridesharinghc.data.constants.Labels.GET_A_RIDE
import com.example.ridesharinghc.data.constants.Labels.OFFER_A_RIDE

/**
 * Composable function [HomeScreenActionRow] that displays the action buttons for "Get a Ride" and "Offer a Ride".
 */
@Composable
fun HomeScreenActionRow(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionBox(
            icon = painterResource(id = R.drawable.ic_get_ride),
            text = GET_A_RIDE,
            onClick = {
                val intent = Intent(context, RideRequestScreen::class.java)
                context.startActivity(intent)
            },
            tag = "getARideButton"
        )
        ActionBox(
            icon = painterResource(id = R.drawable.ic_offer_ride),
            text = OFFER_A_RIDE,
            onClick = {
                val intent = Intent(context, OfferRideScreen::class.java)
                context.startActivity(intent)
            },
            tag = "offerARideButton"
        )
    }
}
