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

        fun createIntent(context: Context, chatId: String, otherUserId: String): Intent {
            return Intent(context, ChatActivity::class.java).apply {
                putExtra(EXTRA_CHAT_ID, chatId)
                putExtra(EXTRA_OTHER_USER_ID, otherUserId)
            }
        }
    }
}
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
        // Back button and user email display
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

fun sendMessage(db: FirebaseFirestore, chatId: String, messageText: String) {
    val message = mapOf(
        "text" to messageText,
        "senderId" to FirebaseAuth.getInstance().currentUser?.uid,
        "timestamp" to System.currentTimeMillis()
    )
    db.collection("chats").document(chatId).collection("messages").add(message)
}

fun endChat(db: FirebaseFirestore, chatId: String, onBackClick: () -> Unit) {
    db.collection("chats").document(chatId).delete().addOnSuccessListener {
        onBackClick() // Navigate back after chat deletion
    }
}
