package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ridesharinghc.activities.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginButtonVisible() {
        composeTestRule.setContent {
            LoginScreen(onBackClick = {})
        }
        // Check that the login button is displayed
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun testSuccessfulLogin() {
        composeTestRule.setContent {
            LoginScreen(onBackClick = {})
        }
        // Type in email and password and click login
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password")
        composeTestRule.onNodeWithText("Login").performClick()

        // I need to add more assertions later here to verify navigation and actions
    }
}
