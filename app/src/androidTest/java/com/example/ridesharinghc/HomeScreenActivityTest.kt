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
        // Verify that the "Get a Ride" and "Offer a Ride" buttons are visible on the HomeScreen
        composeTestRule.onNodeWithTag("getARideButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("offerARideButton").assertIsDisplayed()
    }

    /*
    @Test
    fun testNavigationToRideRequestScreen() {
        // Perform click on the "Get a Ride" button
        composeTestRule.onNodeWithTag("getARideButton").performClick()

        // Wait for idle to ensure the UI is fully rendered
        composeTestRule.waitForIdle()

        // Ensure that the RideRequestScreen is displayed by checking the visibility of elements in the target screen
        composeTestRule.onNodeWithTag("rideRequestScreen").assertIsDisplayed()
    }
    */
}