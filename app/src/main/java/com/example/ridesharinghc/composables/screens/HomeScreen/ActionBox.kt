package com.example.ridesharinghc.composables.screens.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable function [ActionBox] that displays an action box with an icon and text.
 * Used for actions like "Get a Ride" and "Offer a Ride" on the home screen.
 *
 * @param icon Painter representing the icon to display.
 * @param text Text label for the action.
 * @param onClick Lambda function executed when the box is clicked.
 * @param tag Tag used for testing.
 */
@Composable
fun ActionBox(
    icon: Painter,
    text: String,
    onClick: () -> Unit,
    tag: String
) {
    Column(
        modifier = Modifier
            .size(150.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable(onClick = onClick)
            .testTag(tag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 16.sp)
    }
}