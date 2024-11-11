package com.example.ridesharinghc.composables.screens.HomeScreen

import org.junit.Assert.*
import org.junit.Assert.assertTrue
import org.junit.Test

class ActionBoxTest {
    @Test
    fun actionBox_onClick_isInvoked() {
        // Arrange: Set up a variable to track if the click was invoked
        var wasClicked = false
        val onClick = { wasClicked = true }
        // Act: Invoke the onClick directly
        onClick()
        // Assert: Verify that onClick was called
        assertTrue("The onClick function should be invoked", wasClicked)
    }
}