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
import com.example.ridesharinghc.data.END_CHAT
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
    val db = FirebaseFirestore.getInstance()
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var otherUserEmail by remember { mutableStateOf("Unknown User") }

    fetchUserEmail(db, otherUserId) { email ->
        otherUserEmail = email ?: "Unknown User"
    }

    fetchMessages(db, chatId) { fetchedMessages ->
        messages = fetchedMessages
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        ChatHeader(otherUserEmail, onBackClick)
        Spacer(modifier = Modifier.height(16.dp))

        MessageList(messages, otherUserId, Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))

        MessageInputField(messageText) { text ->
            sendMessage(db, FirebaseAuth.getInstance(), chatId, text)
            messageText = ""
        }
        Spacer(modifier = Modifier.height(16.dp))

        EndChatButton { endChat(db, chatId, onBackClick) }
    }
}

/**
 * Fetches the email of the other user from Firestore.
 */
@Composable
fun fetchUserEmail(db: FirebaseFirestore, userId: String, onEmailFetched: (String?) -> Unit) {
    LaunchedEffect(userId) {
        db.collection("userProfiles").document(userId).get().addOnSuccessListener { document ->
            onEmailFetched(document.getString("email"))
        }
    }
}

/**
 * Fetches chat messages in real-time from Firestore.
 */
@Composable
fun fetchMessages(db: FirebaseFirestore, chatId: String, onMessagesFetched: (List<Map<String, Any>>) -> Unit) {
    LaunchedEffect(chatId) {
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    onMessagesFetched(snapshot.documents.mapNotNull { it.data })
                }
            }
    }
}

/**
 * Displays the back button and other user's email.
 */
@Composable
fun ChatHeader(otherUserEmail: String, onBackClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
}

/**
 * Displays the list of messages.
 */
@Composable
fun MessageList(messages: List<Map<String, Any>>, otherUserId: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
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
}

/**
 * Displays the input field and send button for messages.
 */
@Composable
fun MessageInputField(messageText: String, onMessageSend: (String) -> Unit) {
    var text by remember { mutableStateOf(messageText) }

    Row(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .background(Color.LightGray, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
        Button(onClick = {
            onMessageSend(text)
            text = ""
        }) {
            Text("Send")
        }
    }
}

/**
 * Displays a button to end the chat session.
 */
@Composable
fun EndChatButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Red, shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
    ) {
        Text(END_CHAT, color = Color.White)
    }
}
