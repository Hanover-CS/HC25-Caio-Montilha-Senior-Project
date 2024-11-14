package com.example.ridesharinghc.composables.screens.common

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Composable function [SubmitButton] for submitting ride requests or offers.
 *
 * @param onBackClick Action to perform after submission.
 * @param dropOffLocation Drop-off location string.
 * @param date Date of the ride.
 * @param time Time of the ride.
 * @param notes Additional notes for the ride.
 */
@Composable
fun SubmitButton(
    onBackClick: () -> Unit,
    dropOffLocation: String,
    date: String,
    time: String,
    notes: String
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val request = mapOf(
                "dropOffLocation" to dropOffLocation,
                "date" to date,
                "time" to time,
                "notes" to notes,
                "userId" to userId
            )

            FirebaseFirestore.getInstance().collection("rideRequests")
                .add(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Request submitted", Toast.LENGTH_SHORT).show()
                    onBackClick()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to submit request", Toast.LENGTH_SHORT).show()
                }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text("Request Ride", color = Color.White, fontSize = 16.sp)
    }
}
