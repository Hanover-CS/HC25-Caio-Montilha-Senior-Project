package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import com.example.ridesharinghc.activities.HomeScreenActivity
import org.junit.Rule
import org.junit.Test

class HomeScreenActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HomeScreenActivity>()

    @Test
    fun testAllButtonsAreDisplayed() {
        composeTestRule.onNodeWithTag("getARideButton").assertIsDisplayed() // Verify "Get a Ride" button is visible
        composeTestRule.onNodeWithTag("offerARideButton").assertIsDisplayed() // Verify "Offer a Ride" button is visible
    }

    @Test
    fun testNavigationToRideRequestScreen() {
        // Perform click on the "Get a Ride" button
        composeTestRule.onNodeWithTag("getARideButton").performClick()

        // Verify that the RideRequestScreen is displayed
        composeTestRule.onNodeWithTag("rideRequestScreen").assertIsDisplayed() // Verify the target screen is displayed
    }
}
