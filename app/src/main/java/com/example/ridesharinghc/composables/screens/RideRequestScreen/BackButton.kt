package com.example.ridesharinghc.composables.screens.RideRequestScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ridesharinghc.R
import androidx.compose.foundation.layout.size

/**
 * Composable function [BackButton] displays a button to navigate back to the previous screen.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun BackButton(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(painterResource(id = R.drawable.arrow), contentDescription = "Back", modifier = Modifier.size(32.dp))
        }
    }
}
