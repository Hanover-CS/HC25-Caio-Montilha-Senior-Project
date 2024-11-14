package com.example.ridesharinghc.composables.screens.OfferRideScreen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Composable function [OfferSubmitButton] displays a button that, when clicked, submits
 * a ride offer to Firebase Firestore with the provided details, including driver name,
 * pickup location, date, time, and available seats.
 *
 * @param onBackClick Lambda function to handle navigation back after a successful offer submission.
 * @param pickupLocation String representing the selected pickup location.
 */
@Composable
fun OfferSubmitButton(
    onBackClick: () -> Unit,
    pickupLocation: String
) {
    val context = LocalContext.current
    val driverName = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val seatsAvailable = remember { mutableStateOf("") }

    Button(
        onClick = {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val offer = hashMapOf(
                "driverName" to driverName.value,
                "pickupLocation" to pickupLocation,
                "date" to date.value,
                "time" to time.value,
                "seatsAvailable" to seatsAvailable.value,
                "userId" to userId
            )

            FirebaseFirestore.getInstance().collection("rideOffers")
                .add(offer)
                .addOnSuccessListener {
                    Toast.makeText(context, "Ride offered successfully", Toast.LENGTH_SHORT).show()
                    onBackClick()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to offer ride", Toast.LENGTH_SHORT).show()
                }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text("Offer Ride", color = Color.White, fontSize = 16.sp)
    }
}