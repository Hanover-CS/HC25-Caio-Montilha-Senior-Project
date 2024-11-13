package com.example.ridesharinghc.composables.screens.LoginRegisterScreen

import android.content.Context
import android.widget.Toast
import com.example.ridesharinghc.firebase.FirebaseAuthHelper

/**
 * Signs up the user using Firebase Authentication with the provided email and password.
 * Uses FirebaseAuthHelper to handle all Firebase operations.
 *
 * @param email User's email address for registration.
 * @param password User's chosen password for registration.
 * @param context Context used for displaying Toast messages.
 * @param onSuccess Lambda function to execute upon successful sign-up and profile data storage.
 */
fun signUpUser(email: String, password: String, context: Context, onSuccess: () -> Unit) {
    FirebaseAuthHelper.signUpUser(email, password, context) {
        onSuccess()
    }
}
