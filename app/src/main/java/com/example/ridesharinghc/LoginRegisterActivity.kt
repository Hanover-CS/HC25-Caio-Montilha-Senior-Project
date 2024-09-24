package com.example.ridesharinghc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.verticalScroll
import com.example.ridesharinghc.ui.theme.LightBlue
import com.example.ridesharinghc.ui.theme.LogoBlack
import com.example.ridesharinghc.ui.theme.LogoWhite
import com.example.ridesharinghc.ui.theme.SoftBlue
import androidx.compose.ui.tooling.preview.Preview
import com.example.ridesharinghc.ui.theme.LogoBlue

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .border(8.dp, LogoBlue)
            .padding(16.dp)
    ) {
        // I am using a Column to arrange the content vertically inside the Box
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

            Text(
                text = "Create Account",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email placeholder
            Text(
                text = "Email Input Placeholder",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password placeholder
            Text(
                text = "Password Input Placeholder",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password placeholder
            Text(
                text = "Confirm Password Input Placeholder",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { /* Placeholder for sign-up action */ }) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Already have an account?")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { /* Placeholder for login action */ }) {
                Text("Login")
            }
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
