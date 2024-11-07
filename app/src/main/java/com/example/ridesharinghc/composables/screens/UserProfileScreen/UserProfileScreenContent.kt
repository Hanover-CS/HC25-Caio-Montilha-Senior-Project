package com.example.ridesharinghc.composables.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ridesharinghc.R
import com.example.ridesharinghc.firebase.uploadProfilePicture
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.example.ridesharinghc.composables.screens.UserProfileScreen.UserProfileTextField

/**
 * Composable function [UserProfileScreenContent] displays the user profile UI.
 * It shows fields for the user's name, email, phone number, and profile picture.
 * Users can update their name and phone number, and change their profile picture by
 * selecting a new image. The changes are saved to Firebase Firestore and Storage.
 *
 * @param onBackClick Lambda function to handle the back button action.
 */
@Composable
fun UserProfileScreenContent(onBackClick: () -> Unit) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf<String?>(null) }

    // Image picker launcher for selecting a profile picture
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            uploadProfilePicture(uri, currentUser?.uid, storage, context) { imageUrl ->
                profileImageUrl = imageUrl
                currentUser?.uid?.let { uid ->
                    db.collection("userProfiles").document(uid).update("profileImageUrl", imageUrl)
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update profile URL in Firestore", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    // Load existing profile data from Firestore
    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { uid ->
            db.collection("userProfiles").document(uid).get()
                .addOnSuccessListener { document ->
                    name = document.getString("name") ?: ""
                    email = document.getString("email") ?: ""
                    phoneNumber = document.getString("phone") ?: ""
                    profileImageUrl = document.getString("profileImageUrl")
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // UI layout for displaying and editing user profile information
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile picture with click-to-change functionality
        Image(
            painter = rememberAsyncImagePainter(model = profileImageUrl ?: R.drawable.ic_person),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
                .clickable {
                    launcher.launch("image/*")
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Editable text fields for user profile data
        UserProfileTextField(value = name, onValueChange = { name = it }, label = "Name")
        UserProfileTextField(value = email, onValueChange = { email = it }, label = "Email", isEnabled = false)
        UserProfileTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = "Phone Number", keyboardType = KeyboardType.Phone)

        Spacer(modifier = Modifier.height(24.dp))

        // Save button to update profile information in Firestore
        Button(
            onClick = {
                val updatedData = mapOf(
                    "name" to name,
                    "phone" to phoneNumber
                )
                currentUser?.uid?.let { uid ->
                    db.collection("userProfiles").document(uid).update(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "Save", color = Color.White, fontSize = 16.sp)
        }
    }
}
