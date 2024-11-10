package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.google.android.libraries.places.api.Places
import com.example.ridesharinghc.composables.screens.OfferRideScreenContent
import com.example.ridesharinghc.BuildConfig

/**
 * [OfferRideScreen] activity allows users to offer a ride within the RideSharingHC app.
 * It displays a map for selecting a pickup location, and provides fields for the driver's name,
 * pickup/drop-off location, date, time, and available seats.
 * Once submitted, the ride offer is saved to Firebase Firestore.
 * The activity initializes the Places API with the configured MAPS_KEY.
 */
class OfferRideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_KEY)
        }

        setContent {
            RideSharingHCTheme {
                OfferRideScreenContent(onBackClick = { finish() })
            }
        }
    }
}
