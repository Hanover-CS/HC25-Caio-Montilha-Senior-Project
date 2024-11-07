package com.example.ridesharinghc.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.LogoBlue
import kotlinx.coroutines.delay

/**
 * Composable function [SplashScreen] displays the app logo in the center of the screen.
 * After a delay of 3 seconds, it triggers the onTimeout callback to proceed to the next screen.
 *
 * @param onTimeout Lambda function to be called after the splash screen timeout.
 */
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

    // Delay effect for the splash screen before calling the onTimeout function
    LaunchedEffect(key1 = true) {
        delay(3000L)
        onTimeout()
    }
}