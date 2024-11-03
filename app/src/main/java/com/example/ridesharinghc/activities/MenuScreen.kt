package com.example.ridesharinghc.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth

/**
 * [MenuScreen] activity displays the main menu of the RideSharingHC app.
 * It provides navigation options to various screens, including Profile, Rides,
 * Messages, and Logout. The Logout option signs the user out of Firebase Authentication.
 */
class MenuScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                MenuScreenContent(onBackClick = { finish() })
            }
        }
    }
}

/**
 * Composable function [MenuScreenContent] displays the UI for the menu screen.
 * It includes options for navigating to the Profile, Rides, and Messages screens,
 * as well as an option to log out. Each option is represented as a clickable row.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun MenuScreenContent(onBackClick: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back arrow to return to HomeScreenActivity
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow2),
                contentDescription = "Back",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu Title
        Text(
            text = "Menu",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Menu options
        MenuOption(
            icon = painterResource(id = R.drawable.ic_person),
            text = "Profile",
            onClick = {
                val intent = Intent(context, UserProfileScreen::class.java)
                context.startActivity(intent)
            }
        )

        MenuOption(
            icon = painterResource(id = R.drawable.ic_rides),
            text = "Rides",
            onClick = {
                val intent = Intent(context, RidesScreen::class.java)
                context.startActivity(intent)
            }
        )

        MenuOption(
            icon = painterResource(id = R.drawable.ic_messages),
            text = "Messages",
            onClick = {
                val intent = Intent(context, MessagesScreen::class.java)
                context.startActivity(intent)
            }
        )

        MenuOption(
            icon = painterResource(id = R.drawable.ic_logout),
            text = "Logout",
            onClick = {
                // Sign out from Firebase Authentication
                FirebaseAuth.getInstance().signOut()

                // Redirect to LoginActivity
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        )
    }
}

/**
 * Composable function [MenuOption] displays an individual menu option with an icon and label.
 * Each option is a clickable row that navigates to a different screen or performs an action.
 *
 * @param icon Icon representing the menu option.
 * @param text Label for the menu option.
 * @param onClick Lambda function executed when the option is clicked.
 */
@Composable
fun MenuOption(icon: Painter, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}
