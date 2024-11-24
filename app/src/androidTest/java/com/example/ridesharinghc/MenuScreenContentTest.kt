package com.example.ridesharinghc

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ridesharinghc.composables.screens.MenuScreen.MenuOption
import com.example.ridesharinghc.composables.screens.MenuScreen.MenuScreenContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(AndroidJUnit4::class)
class MenuScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBackButtonClickInvokesOnBackClick() {
        val backClicked = AtomicBoolean(false)

        composeTestRule.setContent {
            MenuScreenContent(onBackClick = { backClicked.set(true) })
        }

        // Perform click on the back button
        composeTestRule.onNodeWithTag("BackButton").performClick()

        // Assert that the onBackClick was triggered
        assert(backClicked.get())
    }
}
