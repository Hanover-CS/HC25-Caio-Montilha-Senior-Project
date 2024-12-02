package com.example.ridesharinghc.composables.screens.UserProfileScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream
import com.google.firebase.firestore.FirebaseFirestore
import com.example.ridesharinghc.composables.screens.UserProfileScreen.decodeBase64ToBitmap

/**
 * Encodes an image to Base64 and saves it to Firestore for the specified user.
 *
 * @param uri URI of the selected image file.
 * @param userId ID of the user, used for identifying the Firestore document.
 * @param context Context used for displaying Toast messages.
 * @param db Firestore instance to save the Base64 string.
 * @param onSuccess Lambda function to execute after successfully saving the image.
 */
fun uploadProfilePicture(uri: Uri, userId: String?, context: Context, db: FirebaseFirestore, onSuccess: () -> Unit) {
    if (userId == null) {
        Toast.makeText(context, "User ID is null", Toast.LENGTH_SHORT).show()
        return
    }

    try {
        // Convert the image to Bitmap
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            @Suppress("DEPRECATION")
            android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        // Encode the bitmap to Base64
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val base64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)

        // Save Base64 string to Firestore
        db.collection("userProfiles").document(userId)
            .update("profileImageBase64", base64String)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile image updated successfully", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to update image: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    } catch (e: Exception) {
        Toast.makeText(context, "Error processing image: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

