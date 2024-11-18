package com.example.ridesharinghc.data

data class RideHistory(
    val rideId: String,
    val userId: String,
    val pickupLocation: String,
    val dropOffLocation: String,
    val date: String,
    val time: String,
    val status: String // "Completed", "Cancelled"
)
