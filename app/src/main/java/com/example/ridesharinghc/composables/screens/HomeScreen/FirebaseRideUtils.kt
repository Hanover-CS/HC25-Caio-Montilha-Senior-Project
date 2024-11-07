package com.example.ridesharinghc.utils

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Deletes a ride offer from Firebase Firestore.
 *
 * @param offerId The ID of the offer to delete.
 */
fun deleteOffer(offerId: String) {
    val offerRef = FirebaseFirestore.getInstance().collection("rideOffers").document(offerId)
    offerRef.delete()
        .addOnFailureListener { e ->
            // Handle the failure, e.g., log or show an error message if needed
            e.printStackTrace()
        }
}

/**
 * Deletes a ride request from Firebase Firestore.
 *
 * @param requestId The ID of the request to delete.
 */
fun deleteRequest(requestId: String) {
    val requestRef = FirebaseFirestore.getInstance().collection("rideRequests").document(requestId)
    requestRef.delete()
        .addOnFailureListener { e ->
            // Handle the failure, e.g., log or show an error message if needed
            e.printStackTrace()
        }
}