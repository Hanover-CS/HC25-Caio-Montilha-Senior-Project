package com.example.ridesharinghc.composables.screens

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Saves authentication data for the user to Firebase Firestore.
 *
 * @param userId Unique identifier of the user in Firebase Authentication.
 * @param email User's email address.
 * @param password User's password.
 * @param onSuccess Lambda function to execute upon successful data save.
 */
fun saveAuthDataToFirebase(userId: String, email: String, password: String, onSuccess: () -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val authRef = firestore.collection("userAuthData")
    val authData = mapOf(
        "email" to email,
        "password" to password
    )
    authRef.document(userId).set(authData)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { /* Handle errors, could log or show a toast here */ }
}
