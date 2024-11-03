package com.example.ridesharinghc.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * [ChatActivity] represents a screen where users can send and receive messages in a specific chat.
 * It handles chat setup, loading messages in real-time, and sending new messages. The activity also
 * allows ending the chat, which removes it from Firestore.
 */
class ChatActivity : ComponentActivity() {
    private lateinit var chatId: String
    private lateinit var otherUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatId = intent.getStringExtra(EXTRA_CHAT_ID)!!
        otherUserId = intent.getStringExtra(EXTRA_OTHER_USER_ID)!!

        setContent {
            ChatScreen(chatId = chatId, otherUserId = otherUserId, onBackClick = { finish() })
        }
    }

    companion object {
        private const val EXTRA_CHAT_ID = "chat_id"
        private const val EXTRA_OTHER_USER_ID = "other_user_id"

        /**
         * Creates an intent to launch [ChatActivity] with the specified chat and user IDs.
         *
         * @param context Context from which the intent is created.
         * @param chatId ID of the chat.
         * @param otherUserId ID of the other user in the chat.
         * @return Intent to start [ChatActivity].
         */
        fun createIntent(context: Context, chatId: String, otherUserId: String): Intent {
            return Intent(context, ChatActivity::class.java).apply {
                putExtra(EXTRA_CHAT_ID, chatId)
                putExtra(EXTRA_OTHER_USER_ID, otherUserId)
            }
        }
    }
}

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
