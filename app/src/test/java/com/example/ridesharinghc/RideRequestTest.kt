package com.example.ridesharinghc

import com.example.ridesharinghc.data.RideRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class RideRequestTest {

    @Test
    fun createRideRequest_validData() {
        // Creating a RideRequest object with valid data
        val rideRequest = RideRequest("1", "Home", "Airport", "5:00 PM", "Need a ride for 2")

        // Asserting that the data in the RideRequest object matches the expected values
        assertEquals("Home", rideRequest.pickupLocation)
        assertEquals("Airport", rideRequest.dropOffLocation)
        assertEquals("5:00 PM", rideRequest.time)
        assertEquals("Need a ride for 2", rideRequest.notes)
    }
}