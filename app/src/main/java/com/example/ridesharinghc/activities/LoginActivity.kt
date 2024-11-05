package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.LoginScreen.LoginScreen
import com.google.firebase.auth.FirebaseAuth

/**
 * [LoginActivity] handles the login functionality of the RideSharingHC app.
 * It uses Firebase Authentication to verify user credentials. Upon successful login,
 * it checks for the user's profile data in Firebase Firestore. If found, it navigates
 * the user to the HomeScreenActivity. The UI is built using Jetpack Compose.
 */
class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance() // Initialize Firebase Authentication
        setContent {
            RideSharingHCTheme {
                LoginScreen(auth = auth, onBackClick = { finish() })
            }
        }
    }
}
