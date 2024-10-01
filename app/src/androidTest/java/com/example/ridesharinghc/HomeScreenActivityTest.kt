package com.example.ridesharinghc

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.ridesharinghc.activities.HomeScreenActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
class HomeScreenActivityTest {

    @Rule @JvmField
    val activityRule = ActivityScenarioRule(HomeScreenActivity::class.java)

    @Test
    fun testAllButtonsAreDisplayed() {
        onView(withText("Get a Ride")).check(matches(isDisplayed()))
        onView(withText("Offer a Ride")).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToRideRequestScreen() {
        onView(withText("Get a Ride")).perform(click())
        onView(withText("Drop-off Location")).check(matches(isDisplayed())) // Verifies if is reaching the RideRequestScreen
    }
}
