package com.example.ridesharinghc.composables.screens.MessagesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
