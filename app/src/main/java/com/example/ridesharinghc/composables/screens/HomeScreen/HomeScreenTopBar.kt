package com.example.ridesharinghc.composables.screens.HomeScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.activities.MenuScreen
import com.example.ridesharinghc.activities.UserProfileScreen
import androidx.compose.ui.Alignment

/**
 * Composable function [HomeScreenTopBar] that displays the top row of buttons for navigation.
 */
@Composable
fun HomeScreenTopBar(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            val intent = Intent(context, MenuScreen::class.java)
            context.startActivity(intent)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.menu_three_lines),
                contentDescription = "Menu",
                modifier = Modifier.size(42.dp)
            )
        }

        IconButton(onClick = {
            val intent = Intent(context, UserProfileScreen::class.java)
            context.startActivity(intent)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Profile",
                modifier = Modifier.size(38.dp)
            )
        }
    }
}
