package com.example.ridesharinghc.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.LogoBlue
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
 * [LoginRegisterActivity] serves as the main activity for user registration and navigation.
 * It provides a form to allow new users to create an account using their email and password.
 * User data is stored in Firebase, utilizing Firebase Authentication for user account creation
 * and Firebase Firestore for additional profile data.
 */

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


/**
 * Composable function [LoginRegisterScreen] that displays the registration form.
 * This screen allows users to input their email, password, and confirm their password.
 * Upon successful account creation, users are directed to the login screen.
 *
 * @param navController Optional [NavController] for navigating between screens.
 */
@Composable
fun LoginRegisterScreen(navController: NavController?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val auth = remember { FirebaseAuth.getInstance() }

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
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Create Account", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.endsWith("@hanover.edu")) {
                        if (password == confirmPassword) {
                            signUpUser(auth, email, password, context) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Congratulations! You have created your account. Let’s move to the Login page."
                                    )
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Passwords do not match, please try again."
                                )
                            }
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Please use your @hanover.edu email address to register."
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

            Button(onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Login")
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * Signs up the user using Firebase Authentication with the provided email and password.
 *
 * @param auth Firebase Authentication instance.
 * @param email User's email address for registration.
 * @param password User's chosen password for registration.
 * @param context Context used for displaying Toast messages.
 * @param onSuccess Lambda function to execute upon successful sign-up and profile data storage.
 */
fun signUpUser(auth: FirebaseAuth, email: String, password: String, context: android.content.Context, onSuccess: () -> Unit) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    saveAuthDataToFirebase(userId, email, password) {
                        saveUserProfileToFirebase(userId, email) {
                            onSuccess()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Sign-up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}

/**
 * Saves authentication data for the user to Firebase Firestore.
 *
 * @param userId Unique identifier of the user in Firebase Authentication.
 * @param email User's email address.
 * @param password User's password.
 * @param onSuccess Lambda function to execute upon successful data save.
 */
fun saveAuthDataToFirebase(userId: String, email: String, password: String, onSuccess: () -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val authRef = firestore.collection("userAuthData")
    val authData = mapOf(
        "email" to email,
        "password" to password
    )
    authRef.document(userId).set(authData)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { /* Handle errors, could log or show a toast here */ }
}

/**
 * Saves additional profile data for the user to Firebase Firestore.
 *
 * @param userId Unique identifier of the user in Firebase Authentication.
 * @param email User's email address.
 * @param onSuccess Lambda function to execute upon successful profile save.
 */
fun saveUserProfileToFirebase(userId: String, email: String, onSuccess: () -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val profileRef = firestore.collection("userProfiles")
    val profileData = mapOf(
        "email" to email,
        "name" to "",
        "phone" to ""
    )
    profileRef.document(userId).set(profileData)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { /* Handle errors, could log or show a toast here */ }
}

