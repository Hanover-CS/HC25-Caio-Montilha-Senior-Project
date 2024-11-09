package com.example.ridesharinghc.composables.screens.RideRequestScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.example.ridesharinghc.composables.screens.RideRequestScreen.*
import androidx.compose.runtime.Composable

/**
 * Composable function [RideRequestScreenContent] displays the UI layout for the ride request screen.
 * It combines various sections such as the back button, location search bar, map, input fields, and submit button.
 *
 * @param onBackClick Lambda function to handle back button action.
 */
@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    val dropOffLocation = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(onBackClick)
        LocationSearchBar(dropOffLocation, date, time, notes)
        MapSection()
        InputFieldsSection(
            dropOffLocation = dropOffLocation,
            date = date,
            time = time,
            notes = notes
        )
        SubmitButton(
            onBackClick = onBackClick,
            dropOffLocation = dropOffLocation.value,
            date = date.value,
            time = time.value,
            notes = notes.value
        )
    }
}
