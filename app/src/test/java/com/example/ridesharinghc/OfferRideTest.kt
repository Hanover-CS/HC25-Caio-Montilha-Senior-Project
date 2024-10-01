package com.example.ridesharinghc

import com.example.ridesharinghc.data.OfferRide
import org.junit.Assert.assertEquals
import org.junit.Test

class OfferRideTest {

    @Test
    fun createOfferRide_validData() {
        // Creating an OfferRide object with valid data
        val offerRide = OfferRide("1", "Downtown", "Hanover College", "10:00 AM", 3)

        // Asserting that the data in the OfferRide object matches the expected values
        assertEquals("Downtown", offerRide.pickupLocation)
        assertEquals("Hanover College", offerRide.dropOffLocation)
        assertEquals("10:00 AM", offerRide.time)
        assertEquals(3, offerRide.availableSeats)
    }
}
