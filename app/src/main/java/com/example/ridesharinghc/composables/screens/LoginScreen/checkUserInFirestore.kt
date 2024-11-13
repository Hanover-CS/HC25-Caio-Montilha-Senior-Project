package com.example.ridesharinghc.composables.screens.LoginScreen

import android.content.Context
import com.example.ridesharinghc.firebase.FirebaseAuthHelper

/**
 * Checks if the user's profile data exists in Firebase Firestore.
 * If the data exists, the user is navigated to the HomeScreenActivity.
 * If not, a message prompts the user to register first.
 *
 * @param userId The unique identifier of the user in Firebase Authentication.
 * @param context Context used to launch HomeScreenActivity and display Toast messages.
 */
fun checkUserInFirestore(userId: String, context: Context) {
    FirebaseAuthHelper.checkUserInFirestore(userId, context)
}
