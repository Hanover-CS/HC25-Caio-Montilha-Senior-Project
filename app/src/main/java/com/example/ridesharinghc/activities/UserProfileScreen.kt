package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.UserProfileScreenContent
import com.example.ridesharinghc.composables.screens.UserProfileScreen.uploadProfilePicture

/**
 * [UserProfileScreen] activity displays and allows editing of the user profile.
 * Users can update their name and phone number, view and change their profile picture,
 * and save these changes to Firebase Firestore. The profile image is stored in Firebase Storage.
 */
class UserProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = { finish() })
            }
        }
    }
}
