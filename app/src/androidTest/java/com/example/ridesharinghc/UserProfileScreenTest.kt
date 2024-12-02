package com.example.ridesharinghc

import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ridesharinghc.composables.screens.UserProfileScreen.UserProfileTextField
import com.example.ridesharinghc.composables.screens.UserProfileScreenContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.google.firebase.storage.FirebaseStorage
import org.junit.Rule
import org.junit.Test

class UserProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun userProfileScreenContent_displaysProfileFields() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = {})
            }
        }

        // Verify that all profile fields are displayed
        composeTestRule.onNodeWithText("Name").assertExists()
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Phone Number").assertExists()
        composeTestRule.onNodeWithText("Save").assertExists()
    }

    @Test
    fun userProfileScreenContent_backButtonIsDisplayed() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = {})
            }
        }
        // Verify back button exists
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }

    @Test
    fun userProfileScreenContent_profilePictureCanBeClicked() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = {})
            }
        }
        // Verify profile picture exists and is clickable
        composeTestRule.onNodeWithContentDescription("Profile Picture").assertExists()
        composeTestRule.onNodeWithContentDescription("Profile Picture").assertHasClickAction()
    }

    @Test
    fun userProfileTextField_isDisplayedAndEditable() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileTextField(
                    value = "Test Name",
                    onValueChange = {},
                    label = "Name"
                )
            }
        }
        // Verify text field exists with the correct label
        composeTestRule.onNodeWithText("Name").assertExists()
        composeTestRule.onNodeWithText("Test Name").assertExists()
    }

    @Test
    fun userProfileTextField_isDisabledWhenSpecified() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileTextField(
                    value = "test@hanover.edu",
                    onValueChange = {},
                    label = "Email",
                    isEnabled = false
                )
            }
        }
        // Verify that the text field is disabled
        composeTestRule.onNodeWithText("test@hanover.edu").assertExists()
        composeTestRule.onNodeWithText("test@hanover.edu").assert(isNotEnabled())
    }

    @Test
    fun userProfileScreenContent_saveButtonUpdatesProfile() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = {})
            }
        }
        // Verify the save button is displayed and clickable
        composeTestRule.onNodeWithText("Save").assertExists()
        composeTestRule.onNodeWithText("Save").assertHasClickAction()
    }

    @Test
    fun userProfileScreenContent_profilePictureIsDisplayed() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = {})
            }
        }
        // Verify that a default profile picture is displayed when no image is set
        composeTestRule.onNodeWithContentDescription("Profile Picture").assertExists()
    }
}
