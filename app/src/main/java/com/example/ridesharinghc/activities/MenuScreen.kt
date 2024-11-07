package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.MenuScreen.MenuScreenContent

/**
 * [MenuScreen] activity displays the main menu of the RideSharingHC app.
 * It provides navigation options to various screens, including Profile, Rides,
 * Messages, and Logout. The Logout option signs the user out of Firebase Authentication.
 */
class MenuScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                MenuScreenContent(onBackClick = { finish() })
            }
        }
    }
}

