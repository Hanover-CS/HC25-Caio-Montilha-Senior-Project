package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.RideRequestScreen.RideRequestScreenContent

/**
 * [RideRequestScreen] activity allows users to request a ride within the RideSharingHC app.
 * It displays a map for selecting pickup and drop-off locations, and includes fields for
 * the date, time, and any additional notes. Users can submit a ride request, which is saved
 * to Firebase Firestore for viewing by other users.
 * The activity sets up the UI theme and initializes the [RideRequestScreenContent] composable.
 */
class RideRequestScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                RideRequestScreenContent(onBackClick = { finish() })
            }
        }
    }
}
