package com.example.ridesharinghc.composables.screens.MessagesScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.activities.ChatActivity
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Composable function [ChatItem] displays information about a single chat,
 * including the other user's contact details and the ride details. Tapping
 * on the chat item navigates the user to the chat screen.
 *
 * @param chat Map containing chat details.
 * @param currentUserId ID of the current user to identify the other participant.
 */
@Composable
fun ChatItem(chat: Map<String, Any>, currentUserId: String?) {
    val otherUserId = if (currentUserId == chat["user1Id"] as? String) chat["user2Id"] as? String else chat["user1Id"] as? String
    var otherUserEmail by remember { mutableStateOf("Unknown User") }
    var otherUserPhone by remember { mutableStateOf("N/A") }
    val rideDetails = chat["rideDetails"] as? Map<String, String>
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // Fetch other user's email and phone from userProfiles
    LaunchedEffect(otherUserId) {
        otherUserId?.let { userId ->
            db.collection("userProfiles").document(userId).get().addOnSuccessListener { document ->
                otherUserEmail = document.getString("email") ?: "Unknown User"
                otherUserPhone = document.getString("phone") ?: "N/A"
            }
        }
    }

    // Display chat item with details and navigate to chat screen on click
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Navigate to chat screen to show messages between the users
                val intent = ChatActivity.createIntent(context, chat["chatId"] as String, otherUserId ?: "")
                context.startActivity(intent)
            },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Chat with: $otherUserEmail", fontSize = 16.sp, color = Color.Black)
            Text("Phone: $otherUserPhone", fontSize = 14.sp, color = Color.DarkGray)
            Text("Location: ${rideDetails?.get("dropOffLocation") ?: rideDetails?.get("pickupLocation")}", fontSize = 14.sp, color = Color.DarkGray)
            Text("Time: ${rideDetails?.get("time")}", fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}