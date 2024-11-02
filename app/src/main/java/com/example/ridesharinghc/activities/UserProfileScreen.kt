package com.example.ridesharinghc.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UserProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                UserProfileScreenContent(onBackClick = { finish() })
            }
        }
    }
}

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

    // Image picker launcher
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

        Image(
            painter = rememberAsyncImagePainter(model = profileImageUrl ?: R.drawable.ic_person),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
                .clickable {
                    // Open image picker when clicked
                    launcher.launch("image/*")
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserProfileTextField(value = name, onValueChange = { name = it }, label = "Name")
        UserProfileTextField(value = email, onValueChange = { email = it }, label = "Email", isEnabled = false)
        UserProfileTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = "Phone Number", keyboardType = KeyboardType.Phone)

        Spacer(modifier = Modifier.height(24.dp))

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

@Composable
fun UserProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isEnabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = isEnabled
    )
}

// Function to upload profile picture to Firebase Storage
fun uploadProfilePicture(uri: Uri, userId: String?, storage: FirebaseStorage, context: Context, onSuccess: (String) -> Unit) {
    if (userId == null) {
        Toast.makeText(context, "User ID is null", Toast.LENGTH_SHORT).show()
        return
    }
    val storageRef = storage.reference.child("profilePictures/$userId.jpg")

    // Log the start of the upload
    Toast.makeText(context, "Uploading image...", Toast.LENGTH_SHORT).show()

    storageRef.putFile(uri)
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { url ->
                onSuccess(url.toString())
                Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to get download URL: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
}
