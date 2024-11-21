package com.example.ridesharinghc.composables.screens.LoginScreen

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.data.constants.Prompts.FILL_ALL_FIELDS_WARNING
import com.example.ridesharinghc.data.constants.ErrorMessages.LOGIN_FAILED_INVALID_CREDENTIALS
import com.example.ridesharinghc.data.constants.ErrorMessages.USER_ID_NOT_FOUND_ERROR
import com.example.ridesharinghc.data.constants
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.example.ridesharinghc.ui.theme.LogoBlue
import com.google.firebase.auth.FirebaseAuth

/**
 * Composable function [LoginScreen] displays the login form.
 * This screen allows users to input their email and password to log in.
 * It includes validation and error handling for Firebase Authentication.
 *
 * @param auth Firebase Authentication instance used to authenticate users.
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun LoginScreen(auth: FirebaseAuth, onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .border(8.dp, LogoBlue)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp).padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Login", fontSize = 24.sp)
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
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    auth.currentUser?.uid?.let { userId ->
                                        checkUserInFirestore(userId, context)
                                    } ?: Toast.makeText(context, constants.ErrorMessages.USER_ID_NOT_FOUND_ERROR, Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, task.exception?.message ?: constants.ErrorMessages.LOGIN_FAILED_INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, FILL_ALL_FIELDS_WARNING, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}
