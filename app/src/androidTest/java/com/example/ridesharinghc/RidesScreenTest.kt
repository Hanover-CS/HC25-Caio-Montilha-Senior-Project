package com.example.ridesharinghc

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.ridesharinghc.composables.screens.RidesScreen.RidesScreenContent
import com.example.ridesharinghc.composables.screens.RidesScreen.RideRequestsSection
import com.example.ridesharinghc.composables.screens.RidesScreen.RideOffersSection
import com.example.ridesharinghc.composables.screens.RidesScreen.RideHistorySection
import com.example.ridesharinghc.composables.screens.RidesScreen.RideItem
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import org.junit.Rule
import org.junit.Test

class RidesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ridesScreenContent_displaysAllSections() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                RidesScreenContent(onBackClick = {})
            }
        }
        // Verify the title
        composeTestRule.onNodeWithText("My Rides").assertExists()
        // Verify section headers
        composeTestRule.onNodeWithText("Your Ride Requests:").assertExists()
        composeTestRule.onNodeWithText("Your Ride Offers:").assertExists()
        composeTestRule.onNodeWithText("Ride History:").assertExists()
    }

    @Test
    fun rideRequestsSection_showsEmptyState() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideRequestsSection(userRequests = emptyList())
            }
        }
        // Verify empty message
        composeTestRule.onNodeWithText("No ride requests found.").assertExists()
    }

    @Test
    fun rideRequestsSection_displaysRequests() {
        val mockRequests = listOf(
            mapOf("dropOffLocation" to "Library", "time" to "10:00 AM"),
            mapOf("pickupLocation" to "Dorm", "time" to "12:00 PM")
        )
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideRequestsSection(userRequests = mockRequests)
            }
        }
        // Verify ride request items
        composeTestRule.onNodeWithText("Location: Library").assertExists()
        composeTestRule.onNodeWithText("Time: 10:00 AM").assertExists()
        composeTestRule.onNodeWithText("Location: Dorm").assertExists()
        composeTestRule.onNodeWithText("Time: 12:00 PM").assertExists()
    }

    @Test
    fun rideOffersSection_showsEmptyState() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideOffersSection(userOffers = emptyList())
            }
        }
        // Verify empty message
        composeTestRule.onNodeWithText("No ride offers found.").assertExists()
    }

    @Test
    fun rideOffersSection_displaysOffers() {
        val mockOffers = listOf(
            mapOf("dropOffLocation" to "Supermarket", "time" to "3:00 PM"),
            mapOf("pickupLocation" to "Gym", "time" to "5:00 PM")
        )
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideOffersSection(userOffers = mockOffers)
            }
        }
        // Verify ride offer items
        composeTestRule.onNodeWithText("Location: Supermarket").assertExists()
        composeTestRule.onNodeWithText("Time: 3:00 PM").assertExists()
        composeTestRule.onNodeWithText("Location: Gym").assertExists()
        composeTestRule.onNodeWithText("Time: 5:00 PM").assertExists()
    }

    @Test
    fun rideHistorySection_showsEmptyState() {
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideHistorySection(rideHistory = emptyList())
            }
        }
        // Verify empty message
        composeTestRule.onNodeWithText("No rides in history.").assertExists()
    }

    @Test
    fun rideHistorySection_displaysHistory() {
        val mockHistory = listOf(
            mapOf("dropOffLocation" to "Clifty Falls Park", "time" to "9:00 AM"),
            mapOf("pickupLocation" to "Office", "time" to "6:00 PM")
        )
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideHistorySection(rideHistory = mockHistory)
            }
        }
        // Verify history items
        composeTestRule.onNodeWithText("Location: Clifty Falls Park").assertExists()
        composeTestRule.onNodeWithText("Time: 9:00 AM").assertExists()
        composeTestRule.onNodeWithText("Location: Office").assertExists()
        composeTestRule.onNodeWithText("Time: 6:00 PM").assertExists()
    }

    @Test
    fun rideItem_displaysDetails() {
        val mockRide = mapOf("dropOffLocation" to "Campus Center", "time" to "2:00 PM")
        composeTestRule.setContent {
            RideSharingHCTheme {
                RideItem(request = mockRide, completed = true)
            }
        }
        // Verify ride item details
        composeTestRule.onNodeWithText("Location: Campus Center").assertExists()
        composeTestRule.onNodeWithText("Time: 2:00 PM").assertExists()
        composeTestRule.onNodeWithText("Status: Completed").assertExists()
    }
}
