// RequestHandler.kt
package com.example.ridesharinghc.composables.screens.HomeScreen

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.ridesharinghc.activities.ChatActivity
import com.example.ridesharinghc.data.constants.ErrorMessages.ERROR_CREATING_CHAT_INVALID_USER_ID
import com.example.ridesharinghc.data.constants
import java.util.UUID

/**
 * Handles accepting a ride request by creating a chat between the requester and the current user.
 *
 * @param context Context used to start the ChatActivity.
 * @param request The ride request data to be accepted.
 */
fun handleAcceptRequest(context: Context, request: Map<String, String>) {
    val db = FirebaseFirestore.getInstance()
    val chatId = UUID.randomUUID().toString()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val requesterId = request["userId"]

    if (currentUserId == null || requesterId == null) {
        // If either ID is null, display an error message and return
        Toast.makeText(context, constants.ErrorMessages.ERROR_CREATING_CHAT_INVALID_USER_ID, Toast.LENGTH_SHORT).show()
        return
    }

    val participants = listOf(requesterId, currentUserId)
    val chatData = mapOf(
        "chatId" to chatId,
        "user1Id" to requesterId,
        "user2Id" to currentUserId,
        "rideDetails" to request,
        "participants" to participants
    )

    db.collection("chats").document(chatId).set(chatData)
        .addOnSuccessListener {
            // Open the messages screen after successfully creating the chat
            val intent = ChatActivity.createIntent(context, chatId, requesterId)
            context.startActivity(intent)
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error creating chat: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}
