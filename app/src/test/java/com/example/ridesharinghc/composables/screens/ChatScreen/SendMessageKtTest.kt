package com.example.ridesharinghc.composables.screens.ChatScreen

import com.example.ridesharinghc.composables.screens.ChatScreen.sendMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.mockito.kotlin.mock

class SendMessageKtTest {
    @Mock
    private lateinit var mockFirestore: FirebaseFirestore
    @Mock
    private lateinit var mockAuth: FirebaseAuth
    @Mock
    private lateinit var mockUser: FirebaseUser
    @Mock
    private lateinit var mockChatDocument: DocumentReference
    @Mock
    private lateinit var mockMessagesCollection: CollectionReference
    private lateinit var chatId: String
    private lateinit var messageText: String
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        chatId = "sampleChatId"
        messageText = "Hello, world!"

        // Mock authentication to simulate a logged-in user
        `when`(mockAuth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn("user123")

        // Mock Firestore structure
        `when`(mockFirestore.collection("chats")).thenReturn(mockMessagesCollection)
        `when`(mockMessagesCollection.document(chatId)).thenReturn(mockChatDocument)
        `when`(mockChatDocument.collection("messages")).thenReturn(mockMessagesCollection)
    }

    @Test
    fun `sendMessage adds message to Firestore with correct data`() {
        // Arrange
        val mockBatch = mock(WriteBatch::class.java)
        `when`(mockFirestore.batch()).thenReturn(mockBatch)
        `when`(mockMessagesCollection.add(anyMap<String, Any>())).thenReturn(mock())
        // Act
        sendMessage(mockFirestore, mockAuth, chatId, messageText)
        // Verify that a message is added to the Firestore messages collection with correct structure
        verify(mockMessagesCollection, times(1)).add(argThat { message ->
            ((message as Map<*, *>)["text"] == messageText) &&
                    (message["senderId"] == "user123") &&
                    (message["timestamp"] != null)
        })
    }

    @Test
    fun `sendMessage does nothing if user is not logged in`() {
        // Arrange
        `when`(mockAuth.currentUser).thenReturn(null)

        // Act
        sendMessage(mockFirestore, mockAuth, chatId, messageText)

        // Verify that no message is added when the user is not logged in
        verify(mockMessagesCollection, never()).add(anyMap<String, Any>())
    }
}
