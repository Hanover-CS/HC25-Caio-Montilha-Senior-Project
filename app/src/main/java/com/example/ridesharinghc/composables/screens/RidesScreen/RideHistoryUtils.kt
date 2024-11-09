package com.example.ridesharinghc.composables.screens.RidesScreen

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Adds a ride request or offer to the ride history collection in Firestore.
 *
 * @param ride Map containing the ride data.
 * @param db Firebase Firestore instance.
 */
fun addToRideHistory(ride: Map<String, String>, db: FirebaseFirestore) {
    db.collection("rideHistory").add(ride)
}