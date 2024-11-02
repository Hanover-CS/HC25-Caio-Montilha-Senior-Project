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
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Messages",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (chats.isEmpty()) {
            Text(
                text = "No messages yet",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            chats.forEach { chat ->
                ChatItem(chat, currentUserId)
            }
        }
    }
}

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
