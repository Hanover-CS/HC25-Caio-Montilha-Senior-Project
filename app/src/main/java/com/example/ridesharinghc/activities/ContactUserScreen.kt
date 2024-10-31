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

        // Recebe o ID do usuário do Intent
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

    // Recupera as informações do usuário no Firestore usando o userId
    LaunchedEffect(userId) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                name = document.getString("name") ?: "N/A"
                phone = document.getString("phone") ?: "N/A"
                email = document.getString("email") ?: "N/A"
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load contact info", Toast.LENGTH_SHORT).show()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Contact Information", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text(text = "Name: $name", fontSize = 18.sp)
        Text(text = "Phone: $phone", fontSize = 18.sp)
        Text(text = "Email: $email", fontSize = 18.sp)
    }
}
