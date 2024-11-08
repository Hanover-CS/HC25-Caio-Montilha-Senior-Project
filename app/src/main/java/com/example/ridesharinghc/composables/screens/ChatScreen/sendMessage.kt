package com.example.ridesharinghc.composables.screens.ChatScreen

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Sends a message to the specified chat in Firestore.
 *
 * @param db Firebase Firestore instance.
 * @param chatId ID of the chat to send the message to.
 * @param messageText The message content to be sent.
 */
fun sendMessage(db: FirebaseFirestore, chatId: String, messageText: String) {
    val message = mapOf(
        "text" to messageText,
        "senderId" to FirebaseAuth.getInstance().currentUser?.uid,
        "timestamp" to System.currentTimeMillis()
    )
    db.collection("chats").document(chatId).collection("messages").add(message)
}
