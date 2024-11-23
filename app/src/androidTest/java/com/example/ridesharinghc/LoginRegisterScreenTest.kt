package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.ridesharinghc.composables.screens.LoginRegisterScreen.LoginRegisterScreen
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented tests for the LoginRegisterScreen.
 */
class LoginRegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule() // Use a ComposeRule instead of AndroidComposeRule

    @Test
    fun testLoginRegisterScreenRendersProperly() {
        // Launch the LoginRegisterScreen composable
        composeTestRule.setContent {
            LoginRegisterScreen(navController = null)
        }

        // Verify that the Email, Password, and Confirm Password fields are displayed
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Confirm Password").assertExists()

        // Verify that the Sign Up button is displayed
        composeTestRule.onNodeWithText("Sign Up").assertExists()

        // Verify that the Login button is displayed
        composeTestRule.onNodeWithText("Login").assertExists()
    }

}
