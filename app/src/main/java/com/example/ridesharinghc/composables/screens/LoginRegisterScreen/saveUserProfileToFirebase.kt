package com.example.ridesharinghc.composables.screens.LoginRegisterScreen

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Saves additional profile data for the user to Firebase Firestore.
 *
 * @param userId Unique identifier of the user in Firebase Authentication.
 * @param email User's email address.
 * @param onSuccess Lambda function to execute upon successful profile save.
 */
fun saveUserProfileToFirebase(userId: String, email: String, onSuccess: () -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val profileRef = firestore.collection("userProfiles")
    val profileData = mapOf(
        "email" to email,
        "name" to "",
        "phone" to ""
    )
    profileRef.document(userId).set(profileData)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { /* Handle errors, could log or show a toast here */ }
}