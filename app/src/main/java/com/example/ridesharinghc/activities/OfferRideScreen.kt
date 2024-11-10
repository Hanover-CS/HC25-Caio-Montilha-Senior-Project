package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.google.android.libraries.places.api.Places
import com.example.ridesharinghc.composables.screens.OfferRideScreenContent
import com.example.ridesharinghc.BuildConfig

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
