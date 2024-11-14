package com.example.ridesharinghc.composables.screens.RideRequestScreen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Composable function [SubmitButton] displays a button for submitting a ride request.
 * When clicked, it saves the ride request details to Firebase Firestore and handles
 * success or failure feedback.
 *
 * @param onBackClick Lambda function to handle navigation back after a successful request submission.
 * @param dropOffLocation String representing the selected drop-off location.
 */
@Composable
fun SubmitButton(
    onBackClick: () -> Unit,
    dropOffLocation: String
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val request = hashMapOf(
                "dropOffLocation" to dropOffLocation,
                "userId" to userId
            )

            FirebaseFirestore.getInstance().collection("rideRequests")
                .add(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Ride requested successfully", Toast.LENGTH_SHORT).show()
                    onBackClick()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to request ride", Toast.LENGTH_SHORT).show()
                }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text("Request Ride", color = Color.White, fontSize = 16.sp)
    }
}
