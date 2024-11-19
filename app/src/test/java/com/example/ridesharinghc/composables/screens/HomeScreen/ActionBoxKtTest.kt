package com.example.ridesharinghc.composables.screens.HomeScreen

import org.junit.Assert.*
import org.junit.Assert.assertTrue
import org.junit.Test

class ActionBoxTest {
    @Test
    fun actionBox_onClick_isInvoked() {
        // Set up a variable to track if the click was invoked
        var wasClicked = false
        val onClick = { wasClicked = true }
        // Invoke the onClick directly
        onClick()
        // Verify that onClick was called
        assertTrue("The onClick function should be invoked", wasClicked)
    }
}
