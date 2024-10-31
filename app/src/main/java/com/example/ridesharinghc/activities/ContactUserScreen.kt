package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme

class ContactUserScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve contact information from the intent
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")

        setContent {
            RideSharingHCTheme {
                ContactUserScreenContent(name ?: "", phone ?: "", email ?: "")
            }
        }
    }
}

@Composable
fun ContactUserScreenContent(name: String, phone: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Contact Information", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text(text = "Name: $name", fontSize = 18.sp)
        Text(text = "Phone: $phone", fontSize = 18.sp)
        Text(text = "Email: $email", fontSize = 18.sp)
    }
}
