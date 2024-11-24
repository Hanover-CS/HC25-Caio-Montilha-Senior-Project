package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ridesharinghc.composables.screens.LoginScreen.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenRendersProperly() {
        val mockAuth = mockk<FirebaseAuth>(relaxed = true)

        composeTestRule.setContent {
            LoginScreen(auth = mockAuth, onBackClick = {})
        }

        composeTestRule.onNodeWithTag("EmailInput").assertExists()
        composeTestRule.onNodeWithTag("PasswordInput").assertExists()
        composeTestRule.onNodeWithTag("LoginButton").assertExists()
    }

    @Test
    fun testBackButtonClickInvokesOnBackClick() {
        val mockAuth = mockk<FirebaseAuth>(relaxed = true)
        val backClicked = AtomicBoolean(false)

        composeTestRule.setContent {
            LoginScreen(
                auth = mockAuth,
                onBackClick = { backClicked.set(true) }
            )
        }

        // Perform click on the back button
        composeTestRule.onNodeWithTag("BackButton").performClick()

        // Assert that the onBackClick was triggered
        assertTrue(backClicked.get())
    }
}

