package com.example.ridesharinghc.composables.screens.RideRequestScreen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Composable function [SubmitButton] provides a button to submit the ride request.
 * On clicking, it saves the request details to Firebase Firestore and navigates back upon success.
 *
 * @param onBackClick Lambda function to handle back navigation after submission.
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
            val request = hashMapOf(
                "dropOffLocation" to dropOffLocation,
                "date" to date,
                "time" to time,
                "notes" to notes,
                "userId" to userId
            )

            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("rideRequests")
                .add(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Request submitted", Toast.LENGTH_SHORT).show()
                    onBackClick()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to submit request", Toast.LENGTH_SHORT).show()
                }
        },
        modifier = Modifier.fillMaxWidth().height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text("Request Ride", color = Color.White, fontSize = 16.sp)
    }
}
