package com.example.ridesharinghc.composables.screens.RideRequestScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

/**
 * Composable function [RequestInputFields] displays input fields for ride request details.
 * It includes fields for drop-off location, date, time, and additional notes.
 *
 * @param dropOffLocation Mutable state holding the selected drop-off location.
 */
@Composable
fun RequestInputFields(dropOffLocation: MutableState<String>) {
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    TextField(
        value = dropOffLocation.value,
        onValueChange = { dropOffLocation.value = it },
        label = { Text("Drop-off Location") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = date.value,
        onValueChange = { date.value = it },
        label = { Text("Date (MM/DD/YYYY)") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    TextField(
        value = time.value,
        onValueChange = { time.value = it },
        label = { Text("Select time") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = notes.value,
        onValueChange = { notes.value = it },
        label = { Text("Add notes") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

