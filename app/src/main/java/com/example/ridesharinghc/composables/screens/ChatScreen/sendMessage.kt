package com.example.ridesharinghc.composables.screens.ChatScreen

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Sends a message to the specified chat in Firestore.
 *
 * @param db Firebase Firestore instance.
 * @param auth FirebaseAuth instance for getting the current user.
 * @param chatId ID of the chat to send the message to.
 * @param messageText The message content to be sent.
 */
fun sendMessage(db: FirebaseFirestore, auth: FirebaseAuth, chatId: String, messageText: String) {
    val userId = auth.currentUser?.uid ?: return
    val message = mapOf(
        "text" to messageText,
        "senderId" to userId,
        "timestamp" to System.currentTimeMillis()
    )
    db.collection("chats").document(chatId).collection("messages").add(message)
}
