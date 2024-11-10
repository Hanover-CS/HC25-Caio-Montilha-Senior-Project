package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.HomeScreen.HomeScreen

/**
 * [HomeScreenActivity] serves as the main screen of the RideSharingHC app.
 * It displays available ride requests and offers, and allows users to create
 * or delete ride offers and requests. Users can also access the menu and profile
 * screens from this activity. The UI is built using Jetpack Compose.
 */
class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                HomeScreen()
            }
        }
    }
}
