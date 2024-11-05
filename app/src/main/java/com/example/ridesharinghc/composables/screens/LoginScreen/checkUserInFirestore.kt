package com.example.ridesharinghc.composables.screens.LoginScreen

import android.content.Intent
import android.widget.Toast
import com.example.ridesharinghc.activities.HomeScreenActivity
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Checks if the user's profile data exists in Firebase Firestore.
 * If the data exists, the user is navigated to the HomeScreenActivity.
 * If not, a message prompts the user to register first.
 *
 * @param userId The unique identifier of the user in Firebase Authentication.
 * @param context Context used to launch HomeScreenActivity and display Toast messages.
 */
fun checkUserInFirestore(userId: String, context: android.content.Context) {
    val firestore = FirebaseFirestore.getInstance()
    val usersRef = firestore.collection("userProfiles")

    usersRef.document(userId).get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                // Navigate to the home screen if user data exists
                val intent = Intent(context, HomeScreenActivity::class.java)
                context.startActivity(intent)
            } else {
                // User data not found in Firestore
                Toast.makeText(
                    context,
                    "User data not found. Please register first.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                context,
                "Failed to retrieve user data: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}