package com.example.ridesharinghc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.ridesharinghc.ui.theme.SoftBlue
import androidx.compose.ui.tooling.preview.Preview
import com.example.ridesharinghc.ui.theme.LogoBlue
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class LoginRegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RideSharingHCTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login_register_screen") {
                    composable("login_register_screen") {
                        LoginRegisterScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginRegisterScreen(navController: NavController?) {
    // State variables to hold user input
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .border(8.dp, LogoBlue)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Create Account", fontSize = 24.sp)

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

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password input field
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up button
            Button(
                onClick = {
                    if (password == confirmPassword) {
                        // Function to save the data to Firebase
                        saveUserToFirebase(email, password) {
                            // Show snackbar when account is created
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Congratulations! You have created your account. Let’s move to the Login page."
                                )
                                // Navigate to login page after the Snackbar is dismissed
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    } else {
                        // Handle mismatching passwords
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Passwords do not match, please try again."
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Already have an account?")

            Spacer(modifier = Modifier.height(8.dp))

            // Login button using context from LocalContext
            Button(onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Login")
            }
        }

        // Snackbar host to show messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// Function to save the user details to Firebase
fun saveUserToFirebase(email: String, password: String, onSuccess: () -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    val userId = usersRef.push().key  // Generating a unique ID for the user
    val userData = mapOf(
        "email" to email,
        "password" to password
    )

    if (userId != null) {
        usersRef.child(userId).setValue(userData)
            .addOnSuccessListener {
                // Data successfully saved, call onSuccess
                onSuccess()
            }
            .addOnFailureListener {
                // Failed to save data
            }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginRegisterScreen() {
    RideSharingHCTheme {
        LoginRegisterScreen(navController = null)
    }
}
