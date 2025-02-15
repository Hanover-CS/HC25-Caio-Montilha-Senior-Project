package com.example.ridesharinghc.composables.screens.LoginRegisterScreen

import android.content.Intent
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
import androidx.navigation.NavController
import com.example.ridesharinghc.R
import com.example.ridesharinghc.activities.LoginActivity
import com.example.ridesharinghc.data.constants.SuccessMessages.ACCOUNT_CREATION_SUCCESS_MESSAGE
import com.example.ridesharinghc.data.constants.Prompts.ALREADY_HAVE_ACCOUNT_PROMPT
import com.example.ridesharinghc.data.constants.Labels.CONFIRM_PASSWORD
import com.example.ridesharinghc.data.CREATE_ACCOUNT_
import com.example.ridesharinghc.data.constants.Labels.EMAIL
import com.example.ridesharinghc.data.constants.Prompts.HANOVER_EMAIL_PROMPT
import com.example.ridesharinghc.data.constants.Labels.PASSWORD
import com.example.ridesharinghc.data.constants.ErrorMessages.PASSWORD_MISMATCH_ERROR
import com.example.ridesharinghc.data.constants
import com.example.ridesharinghc.firebase.FirebaseAuthHelper
import com.example.ridesharinghc.ui.theme.LogoBlue
import com.example.ridesharinghc.ui.theme.SoftBlue
import kotlinx.coroutines.launch

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
            Text(text = CREATE_ACCOUNT_, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(EMAIL) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(PASSWORD) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(CONFIRM_PASSWORD) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        !email.endsWith("@hanover.edu") -> scope.launch {
                            snackbarHostState.showSnackbar(HANOVER_EMAIL_PROMPT)
                        }
                        password != confirmPassword -> scope.launch {
                            snackbarHostState.showSnackbar(constants.ErrorMessages.PASSWORD_MISMATCH_ERROR)
                        }
                        else -> {
                            FirebaseAuthHelper.signUpUser(email, password, context) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(ACCOUNT_CREATION_SUCCESS_MESSAGE)
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Sign Up") }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = ALREADY_HAVE_ACCOUNT_PROMPT)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Login") }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
