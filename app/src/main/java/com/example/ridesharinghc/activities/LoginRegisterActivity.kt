package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ridesharinghc.composables.screens.LoginRegisterScreen.LoginRegisterScreen
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme

/**
 * [LoginRegisterActivity] serves as the main activity for user registration and navigation.
 * It provides a form to allow new users to create an account using their email and password.
 * User data is stored in Firebase, utilizing Firebase Authentication for user account creation
 * and Firebase Firestore for additional profile data.
 */
class LoginRegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RideSharingHCTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login_register_screen") {
                    composable("login_register_screen") {
                        LoginRegisterScreen(navController)
                    }
                }
            }
        }
    }
}
