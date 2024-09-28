package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.LogoBlue
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue

class RideRequestScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                RideRequestScreenContent(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun RideRequestScreenContent(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        // Top bar with icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back arrow icon button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(onClick = { /* TODO: handle profile click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Profile",
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        // Map and form fields
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp, start = 16.dp, end = 16.dp)
        ) {
            // Blank Box for Map
            Box(
                modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, LogoBlue, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Drop-off Location Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Handle location input integrate with google maps in future */ },
                label = { Text("Drop-off Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Handle date input */ },
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Handle time input */ },
                label = { Text("Select time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add Notes Field
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Handle notes input */ },
                label = { Text("Add notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Request Ride Button
            Button(
                onClick = { /* TODO: Handle ride request submission */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LogoBlue)
            ) {
                Text("Request Ride", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
