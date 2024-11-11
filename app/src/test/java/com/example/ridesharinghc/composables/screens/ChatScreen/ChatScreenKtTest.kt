package com.example.ridesharinghc.composables.screens

import com.example.ridesharinghc.composables.screens.ChatScreen.sendMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference
import com.google.android.gms.tasks.Tasks
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class ChatScreenKtTest {

    @Mock
    private lateinit var mockFirestore: FirebaseFirestore

    @Mock
    private lateinit var mockChatsCollection: CollectionReference

    @Mock
    private lateinit var mockMessagesCollection: CollectionReference

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockUser: FirebaseUser

    private lateinit var chatId: String
    private lateinit var messageText: String

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        chatId = "sampleChatId"
        messageText = "Hello, World!"

        // Mock Firebase setup
        `when`(mockFirestore.collection("chats")).thenReturn(mockChatsCollection)
        `when`(mockChatsCollection.document(chatId)).thenReturn(mock())
        `when`(mockChatsCollection.document(chatId).collection("messages")).thenReturn(mockMessagesCollection)

        `when`(mockAuth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn("testUserId")
    }

    @Test
    fun `sendMessage adds message to Firestore`() {
        // Arrange
        `when`(mockMessagesCollection.add(any<Map<String, Any>>())).thenReturn(Tasks.forResult(null))

        // Act
        sendMessage(mockFirestore, mockAuth, chatId, messageText)

        // Assert
        verify(mockMessagesCollection, times(1)).add(any<Map<String, Any>>())
    }
}
