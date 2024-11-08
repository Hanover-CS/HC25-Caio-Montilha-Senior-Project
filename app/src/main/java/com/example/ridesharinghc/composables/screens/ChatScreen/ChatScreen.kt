package com.example.ridesharinghc.composables.screens.ChatScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.composables.screens.ChatScreen.endChat
import com.example.ridesharinghc.composables.screens.ChatScreen.sendMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.auth.FirebaseAuth

/**
 * Composable function [ChatScreen] displays the UI for the chat, including messages, a text input
 * for sending new messages, and options to go back or end the chat.
 *
 * @param chatId ID of the chat.
 * @param otherUserId ID of the other user in the chat.
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun ChatScreen(chatId: String, otherUserId: String, onBackClick: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var otherUserEmail by remember { mutableStateOf("Unknown User") }
    val db = FirebaseFirestore.getInstance()

    // Fetch other user email from Firestore
    LaunchedEffect(otherUserId) {
        db.collection("userProfiles").document(otherUserId).get().addOnSuccessListener { document ->
            otherUserEmail = document.getString("email") ?: "Unknown User"
        }
    }

    // Fetch messages in real-time
    LaunchedEffect(chatId) {
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    messages = snapshot.documents.mapNotNull { it.data }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        // Back button and other user's email display
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Chat with: $otherUserEmail", fontSize = 20.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Display messages
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            messages.forEach { message ->
                Text(
                    text = message["text"].toString(),
                    color = if (message["senderId"] == otherUserId) Color.Blue else Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Message input and send button
        Row(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
            Button(onClick = {
                sendMessage(db, chatId, messageText)
                messageText = ""
            }) {
                Text("Send")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // End Chat button
        Button(
            onClick = {
                endChat(db, chatId, onBackClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Red, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
        ) {
            Text("End Chat", color = Color.White)
        }
    }
}
