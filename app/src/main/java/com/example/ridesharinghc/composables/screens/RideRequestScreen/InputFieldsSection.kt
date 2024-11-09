package com.example.ridesharinghc.composables.screens.RideRequestScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

/**
 * Composable function [InputFieldsSection] displays input fields for ride request details, including drop-off location,
 * date, time, and additional notes. It allows users to input details for their ride request.
 *
 * @param dropOffLocation MutableState for the drop-off location input field.
 * @param date MutableState for the date input field.
 * @param time MutableState for the time input field.
 * @param notes MutableState for the additional notes input field.
 */
@Composable
fun InputFieldsSection(
    dropOffLocation: MutableState<String>,
    date: MutableState<String>,
    time: MutableState<String>,
    notes: MutableState<String>
) {
    TextField(
        value = dropOffLocation.value,
        onValueChange = { dropOffLocation.value = it },
        label = { Text("Drop-off Location") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )

    TextField(
        value = date.value,
        onValueChange = {
            if (it.length <= 10) {
                val formatted = it.replace(Regex("[^0-9]"), "").chunked(2).joinToString("/")
                date.value = formatted
            }
        },
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )

    TextField(
        value = notes.value,
        onValueChange = { notes.value = it },
        label = { Text("Add notes") },
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
}
