package com.example.ridesharinghc.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.SplashScreen.SplashScreen

/**
 * [SplashActivity] displays a splash screen with the app logo and
 * navigates to the Login/Register screen after a 3-second delay.
 * The transition is handled using a coroutine for timing, with error
 * logging in case of navigation issues.
 */
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RideSharingHCTheme {
                SplashScreen { navigateToLoginRegister() }
            }
        }
    }

    /**
     * Navigates to the LoginRegisterActivity. Logs the navigation action and
     * handles any potential errors with error logging.
     */
    private fun navigateToLoginRegister() {
        try {
            Log.d("SplashActivity", "Navigating to Login/Register screen")
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("SplashActivity", "Error navigating: ${e.message}")
        }
    }
}
