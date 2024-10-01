package com.example.ridesharinghc.data

data class RideRequest(
    val id: String,
    val pickupLocation: String,
    val dropOffLocation: String,
    val time: String,
    val notes: String
)
