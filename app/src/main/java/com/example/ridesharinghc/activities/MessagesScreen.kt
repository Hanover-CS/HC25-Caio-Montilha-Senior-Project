package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.composables.screens.MessagesScreen.MessagesScreenContent

/**
 * [MessagesScreen] activity displays a list of chat conversations for the current user.
 * Users can view their active chats and navigate to specific chat screens for each conversation.
 */
class MessagesScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                MessagesScreenContent(onBackClick = { finish() })
            }
        }
    }
}
