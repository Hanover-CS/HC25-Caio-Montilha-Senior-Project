package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.example.ridesharinghc.composables.screens.SplashScreen.SplashScreen
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_displaysLogo() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                SplashScreen(onTimeout = {})
            }
        }
        // Verify the app logo is displayed
        composeTestRule.onNodeWithContentDescription("App Logo").assertExists()
    }

    @Test
    fun splashScreen_triggersTimeoutAfterDelay() {
        var onTimeoutTriggered = false
        composeTestRule.setContent {
            RideSharingHCTheme {
                SplashScreen(onTimeout = { onTimeoutTriggered = true })
            }
        }
        // Advance time by 3 seconds (3000ms) to simulate the delay
        composeTestRule.mainClock.advanceTimeBy(3000L)
        // Verify that the onTimeout
        assert(onTimeoutTriggered)
    }
}
