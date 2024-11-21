package com.example.ridesharinghc.composables.screens.OfferRideScreen

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
import com.example.ridesharinghc.data.DATE_FORMAT
import com.example.ridesharinghc.data.DRIVER_NAME
import com.example.ridesharinghc.data.PICKUP_LOCATION
import com.example.ridesharinghc.data.SEATS_AVAILABLE
import com.example.ridesharinghc.data.TIME


/**
 * Composable function [OfferInputFields] displays input fields for offering a ride,
 * including driver's name, pickup location, date, time, and available seats.
 *
 * @param pickupLocation Mutable state holding the pickup location.
 */
@Composable
fun OfferInputFields(pickupLocation: MutableState<String>) {
    val driverName = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val seatsAvailable = remember { mutableStateOf("") }

    TextField(
        value = driverName.value,
        onValueChange = { driverName.value = it },
        label = { Text(DRIVER_NAME) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = pickupLocation.value,
        onValueChange = { pickupLocation.value = it },
        label = { Text(PICKUP_LOCATION) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = date.value,
        onValueChange = { date.value = it },
        label = { Text(DATE_FORMAT) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    TextField(
        value = time.value,
        onValueChange = { time.value = it },
        label = { Text(TIME) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    TextField(
        value = seatsAvailable.value,
        onValueChange = { seatsAvailable.value = it },
        label = { Text(SEATS_AVAILABLE) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
