package com.example.ridesharinghc.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.LogoBlue
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RideSharingHCTheme {
                SplashScreen { navigateToLoginRegister() }
            }
        }
    }

    private fun navigateToLoginRegister() {
        try {
            Log.d("SplashActivity", "Navigating to Login/Register screen")
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("SplashActivity", "Error navigating: ${e.message}")
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(LogoBlue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo"
        )
    }

    LaunchedEffect(key1 = true) {
        delay(3000L)
        onTimeout()
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    RideSharingHCTheme {
        SplashScreen(onTimeout = {})
    }
}
