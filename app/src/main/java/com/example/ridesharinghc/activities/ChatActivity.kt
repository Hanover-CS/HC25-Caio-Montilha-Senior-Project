package com.example.ridesharinghc.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ridesharinghc.composables.screens.ChatScreen.ChatScreen

/**
 * [ChatActivity] represents a screen where users can send and receive messages in a specific chat.
 * It handles chat setup, loading messages in real-time, and sending new messages. The activity also
 * allows ending the chat, which removes it from Firestore.
 */
class ChatActivity : ComponentActivity() {
    private lateinit var chatId: String
    private lateinit var otherUserId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatId = intent.getStringExtra(EXTRA_CHAT_ID)!!
        otherUserId = intent.getStringExtra(EXTRA_OTHER_USER_ID)!!

        setContent {
            ChatScreen(chatId = chatId, otherUserId = otherUserId, onBackClick = { finish() })
        }
    }

    companion object {
        private const val EXTRA_CHAT_ID = "chat_id"
        private const val EXTRA_OTHER_USER_ID = "other_user_id"

        /**
         * Creates an intent to launch [ChatActivity] with the specified chat and user IDs.
         *
         * @param context Context from which the intent is created.
         * @param chatId ID of the chat.
         * @param otherUserId ID of the other user in the chat.
         * @return Intent to start [ChatActivity].
         */
        fun createIntent(context: Context, chatId: String, otherUserId: String): Intent {
            return Intent(context, ChatActivity::class.java).apply {
                putExtra(EXTRA_CHAT_ID, chatId)
                putExtra(EXTRA_OTHER_USER_ID, otherUserId)
            }
        }
    }
}
