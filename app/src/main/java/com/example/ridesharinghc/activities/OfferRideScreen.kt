package com.example.ridesharinghc.activities

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.google.android.libraries.places.api.Places
import com.example.ridesharinghc.composables.screens.OfferRideScreen.OfferRideScreenContent

/**
 * [OfferRideScreen] activity allows users to offer a ride within the RideSharingHC app.
 * It displays a map for selecting a pickup location, and provides fields for
 * the driver's name, pickup/drop-off location, date, time, and available seats.
 * Once submitted, the ride offer is saved to Firebase Firestore.
 */
class OfferRideScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the Places API
        if (!Places.isInitialized()) {
            val applicationInfo = applicationContext.packageManager.getApplicationInfo(
                applicationContext.packageName,
                PackageManager.GET_META_DATA
            )
            applicationInfo.metaData.getString("com.google.android.geo.API_KEY")
                ?.let { Places.initialize(applicationContext, it) }
        }
        setContent {
            RideSharingHCTheme {
                OfferRideScreenContent(onBackClick = { finish() })
            }
        }
    }
}
