package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ridesharinghc.activities.LoginRegisterScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSignUpWithMismatchedPasswords() {
        composeTestRule.setContent {
            LoginRegisterScreen(navController = null)
        }

        // Input email, password, and a different confirm password
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password1")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("password2")

        // Click the sign-up button
        composeTestRule.onNodeWithText("Sign Up").performClick()

        // Check that the error message is displayed
        composeTestRule.onNodeWithText("Passwords do not match, please try again.").assertExists()
    }
}
