package com.example.ridesharinghc.composables.screens

import com.example.ridesharinghc.firebase.FirebaseAuthHelper

/**
 * Saves authentication data for the user to Firebase Firestore using FirebaseAuthHelper.
 *
 * @param userId Unique identifier of the user in Firebase Authentication.
 * @param email User's email address.
 * @param password User's password.
 * @param onSuccess Lambda function to execute upon successful data save.
 */
fun saveAuthDataToFirebase(userId: String, email: String, password: String, onSuccess: () -> Unit) {
    FirebaseAuthHelper.saveAuthData(userId, email, password, onSuccess)
}
