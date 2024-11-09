package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.RidesScreen.RidesScreenContent

/**
 * [RidesScreen] activity displays the user's ride requests, ride offers, and ride history.
 * Users can view each type of ride in separate sections, organized by requests, offers, and history.
 * Each ride item can be viewed individually, and historical rides are permanently stored.
 */
class RidesScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                RidesScreenContent(onBackClick = { finish() })
            }
        }
    }
}
