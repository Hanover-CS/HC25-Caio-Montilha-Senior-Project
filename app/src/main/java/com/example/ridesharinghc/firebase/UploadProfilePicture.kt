package com.example.ridesharinghc.firebase

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage

/**
 * Uploads a profile picture to Firebase Storage for the specified user.
 *
 * @param uri URI of the selected image file.
 * @param userId ID of the user, used for naming the storage reference.
 * @param storage Firebase Storage instance.
 * @param context Context used for displaying Toast messages.
 * @param onSuccess Lambda function to execute with the URL of the uploaded image on success.
 */
fun uploadProfilePicture(uri: Uri, userId: String?, storage: FirebaseStorage, context: Context, onSuccess: (String) -> Unit) {
    if (userId == null) {
        Toast.makeText(context, "User ID is null", Toast.LENGTH_SHORT).show()
        return
    }
    val storageRef = storage.reference.child("profilePictures/$userId.jpg")

    // Start upload and display a message
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
