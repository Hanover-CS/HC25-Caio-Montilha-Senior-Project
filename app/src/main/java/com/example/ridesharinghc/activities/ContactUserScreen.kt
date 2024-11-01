package com.example.ridesharinghc.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.firestore.FirebaseFirestore

class ContactUserScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Receives the userId from the Intent
        val userId = intent.getStringExtra("userId")

        setContent {
            RideSharingHCTheme {
                ContactUserScreenContent(userId ?: "", onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun ContactUserScreenContent(userId: String, onBackClick: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var name by remember { mutableStateOf("Loading...") }
    var phone by remember { mutableStateOf("Loading...") }
    var email by remember { mutableStateOf("Loading...") }

    // Retrieve user information from Firestore using the userId
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            db.collection("userProfiles").document(userId).get()
                .addOnSuccessListener { document ->
                    name = document.getString("name") ?: "N/A"
                    phone = document.getString("phone") ?: "N/A"
                    email = document.getString("email") ?: "N/A"
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load contact info", Toast.LENGTH_SHORT).show()
                    name = "N/A"
                    phone = "N/A"
                    email = "N/A"
                }
        } else {
            Toast.makeText(context, "User ID not provided", Toast.LENGTH_SHORT).show()
            name = "N/A"
            phone = "N/A"
            email = "N/A"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        // Back arrow at the top-left corner
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        // Centered contact information
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center) // Center the column vertically and horizontally within the Box
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Contact Information", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
            Text(text = "Name: $name", fontSize = 18.sp)
            Text(text = "Phone: $phone", fontSize = 18.sp)
            Text(text = "Email: $email", fontSize = 18.sp)
        }
    }
}
