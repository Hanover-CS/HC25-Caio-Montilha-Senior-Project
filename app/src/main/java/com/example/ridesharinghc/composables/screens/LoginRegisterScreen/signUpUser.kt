package com.example.ridesharinghc.composables.screens.LoginRegisterScreen

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.ridesharinghc.composables.screens.LoginRegisterScreen.saveUserProfileToFirebase
import com.example.ridesharinghc.composables.screens.saveAuthDataToFirebase


/**
 * Signs up the user using Firebase Authentication with the provided email and password.
 *
 * @param auth Firebase Authentication instance.
 * @param email User's email address for registration.
 * @param password User's chosen password for registration.
 * @param context Context used for displaying Toast messages.
 * @param onSuccess Lambda function to execute upon successful sign-up and profile data storage.
 */
fun signUpUser(auth: FirebaseAuth, email: String, password: String, context: Context, onSuccess: () -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    // Save authentication data to Firestore
                    saveAuthDataToFirebase(userId, email, password) {
                        // Save additional profile data to Firestore
                        saveUserProfileToFirebase(userId, email) {
                            onSuccess()
                        }
                    }
                }
            } else {
                // Display a Toast message in case of sign-up failure
                Toast.makeText(context, "Sign-up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}