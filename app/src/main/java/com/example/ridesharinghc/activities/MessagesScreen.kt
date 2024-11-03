package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * [MessagesScreen] activity displays a list of chat conversations for the current user.
 * Users can view their active chats and navigate to specific chat screens for each conversation.
 */
class MessagesScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                MessagesScreenContent(onBackClick = { finish() })
            }
        }
    }
}

/**
 * Composable function [MessagesScreenContent] displays the list of chats the user is
 * involved in. Each chat item allows navigation to a detailed chat screen.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun MessagesScreenContent(onBackClick: () -> Unit) {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    var chats by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    val db = FirebaseFirestore.getInstance()

    // Fetch chats where the current user is a participant
    LaunchedEffect(currentUserId) {
        currentUserId?.let { userId ->
            db.collection("chats")
                .whereArrayContains("participants", userId)
                .get()
                .addOnSuccessListener { snapshot ->
                    chats = snapshot.documents.mapNotNull { it.data }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button to return to the previous screen
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Screen title
        Text(
            text = "Messages",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display message if no chats are available
        if (chats.isEmpty()) {
            Text(
                text = "No messages yet",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            // Display each chat item
            chats.forEach { chat ->
                ChatItem(chat, currentUserId)
            }
        }
    }
}

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
