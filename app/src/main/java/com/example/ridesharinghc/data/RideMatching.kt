package com.example.ridesharinghc.data

data class RideMatching(
    val matchId: String,
    val requestId: String,
    val offerId: String,
    val status: String // "Matched", "Pending", "Declined"
)
