package com.example.ridesharinghc.composables.screens.ChatScreen

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Ends the chat by deleting the chat document from Firestore.
 *
 * @param db Firebase Firestore instance.
 * @param chatId ID of the chat to be deleted.
 * @param onBackClick Lambda function to navigate back after deletion.
 */
fun endChat(db: FirebaseFirestore, chatId: String, onBackClick: () -> Unit) {
    db.collection("chats").document(chatId).delete().addOnSuccessListener {
        onBackClick() // Navigate back after chat deletion
    }
}
