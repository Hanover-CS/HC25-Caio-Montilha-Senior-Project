package com.example.ridesharinghc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.example.ridesharinghc.ui.theme.LogoBlue

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RideSharingHCTheme {
                LoginScreen(onBackClick = { navigateBackToLoginRegisterActivity() })
            }
        }
    }

    // Function to navigate back to LoginRegisterActivity
    private fun navigateBackToLoginRegisterActivity() {
        finish()  // Go back to the previous activity (LoginRegisterActivity)
    }
}

@Composable
fun LoginScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        // Back arrow positioned outside the box, top left of the screen
        IconButton(
            onClick = onBackClick,  // Navigate back to the previous activity
            modifier = Modifier
                .padding(8.dp)  // Set padding from the top left edge of the screen
                .size(48.dp)  // Adjust size of the icon
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(30.dp)  // Adjust size of the arrow
            )
        }

        Box(
            modifier = Modifier
                .width(400.dp) // Ajusta a largura
                .height(950.dp) // Ajusta a altura
                .padding(top = 56.dp)  // Padding no topo
                .padding(bottom = 15.dp)
                .padding(start = 26.dp)
                .background(SoftBlue)
                .border(8.dp, LogoBlue)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Login", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                // Email input field
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password input field
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login button
                Button(
                    onClick = {
                        // Handle login functionality here
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        }
    }
}
