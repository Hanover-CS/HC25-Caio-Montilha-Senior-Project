package com.example.ridesharinghc.data

data class OfferRide(
    val id: String,
    val pickupLocation: String,
    val dropOffLocation: String,
    val time: String,
    val availableSeats: Int
)
