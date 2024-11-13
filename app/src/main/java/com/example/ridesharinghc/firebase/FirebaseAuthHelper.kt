package com.example.ridesharinghc.firebase

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.ridesharinghc.activities.HomeScreenActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * FirebaseAuthHelper handles Firebase Authentication and Firestore operations.
 * It consolidates functions previously spread across multiple files such as
 * signUpUser.kt, saveAuthDataToFirebase.kt, saveUserProfileToFirebase.kt, and
 * checkUserInFirestore.kt, centralizing authentication and user data management.
 */
object FirebaseAuthHelper {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Signs up a new user with email and password, saving their authentication and profile data.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @param context Context for displaying Toast messages.
     * @param onSuccess Lambda function to execute on successful signup.
     */
    fun signUpUser(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Save authentication data and then save the user profile
                        saveAuthData(userId, email, password) {
                            saveUserProfile(userId, email, onSuccess)
                        }
                    }
                } else {
                    Toast.makeText(context, "Sign-up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Logs in an existing user with email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @param onSuccess Lambda function to execute on successful login.
     * @param onFailure Lambda function to handle login failure, providing the error message.
     */
    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Login failed.")
                }
            }
    }

    /**
     * Saves authentication data for the user to Firestore.
     *
     * @param userId The unique identifier for the user.
     * @param email The user's email address.
     * @param password The user's password.
     * @param onSuccess Lambda function to execute after successful data save.
     */
    fun saveAuthData(userId: String, email: String, password: String, onSuccess: () -> Unit) {
        val authData = mapOf("email" to email, "password" to password)
        firestore.collection("userAuthData").document(userId).set(authData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { /* Handle errors if needed */ }
    }

    /**
     * Saves additional profile data for the user to Firestore.
     *
     * @param userId The unique identifier for the user.
     * @param email The user's email address.
     * @param onSuccess Lambda function to execute after successful profile save.
     */
    fun saveUserProfile(userId: String, email: String, onSuccess: () -> Unit) {
        val profileData = mapOf("email" to email, "name" to "", "phone" to "")
        firestore.collection("userProfiles").document(userId).set(profileData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { /* Handle errors if needed */ }
    }

    /**
     * Checks if a user's profile exists in Firestore and navigates to HomeScreen if found.
     *
     * @param userId The unique identifier for the user.
     * @param context Context for navigating to the home screen and displaying Toast messages.
     */
    fun checkUserInFirestore(userId: String, context: Context) {
        firestore.collection("userProfiles").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val intent = Intent(context, HomeScreenActivity::class.java)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context,
                        "User data not found. Please register first.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Failed to retrieve user data: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
