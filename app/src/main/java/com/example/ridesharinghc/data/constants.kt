package com.example.ridesharinghc.data

import androidx.compose.runtime.Composable


object constants {
    // Default coordinates
    object DefaultCoordinates {
        const val DEFAULT_LATITUDE = -33.852
        const val DEFAULT_LONGITUDE = 151.211
    }

    // Error messages
    object ErrorMessages {
        const val UNABLE_TO_GET_LOCATION = "Unable to get location"
        const val ERROR_CREATING_CHAT_INVALID_USER_ID = "Error creating chat: Invalid user ID."
        const val USER_ID_NOT_FOUND_ERROR = "User ID not found. Login failed."
        const val LOGIN_FAILED_INVALID_CREDENTIALS = "Login failed. Please check your credentials."
        const val PASSWORD_MISMATCH_ERROR = "Passwords do not match, please try again."
    }

    // General prompts
    object Prompts {
        const val SEARCH_DROP_OFF_LOCATION = "Search Drop-off Location"
        const val ARE_YOU_SURE_YOU_WANT_TO_ACCEPT_THIS_REQUEST = "Are you sure you want to accept this request?"
        const val HANOVER_EMAIL_PROMPT = "Please use your @hanover.edu email address to register."
        const val ALREADY_HAVE_ACCOUNT_PROMPT = "Already have an account?"
        const val FILL_ALL_FIELDS_WARNING = "Please fill in all fields."
    }

    // Success and informational messages
    object SuccessMessages {
        const val ACCOUNT_CREATION_SUCCESS_MESSAGE = "Congratulations! You have created your account."
        const val NO_MESSAGES_YET = "No messages yet"
    }

    // UI titles and labels
    object Labels {
        const val CONFIRMATION = "Confirmation"
        const val GET_A_RIDE = "Get a Ride"
        const val OFFER_A_RIDE = "Offer a Ride"
        const val RIDE_SHARING_HC = "RideSharingHC"
        const val DRIVER_NAME = "Driver's Name"
        const val PICKUP_LOCATION = "Pickup Location"
        const val DATE_FORMAT = "Date (MM/DD/YYYY)"
        const val SEATS_AVAILABLE = "Seats Available"
        const val TIME = "Time"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val CONFIRM_PASSWORD = "Confirm Password"
    }
}

public const val END_CHAT = "End Chat"
public const val UNKNOWN_USER = "Unknown User"
public const val CURRENT_RIDES_OFFERED_ = "Current Rides Offered:"
public const val CURRENT_REQUESTS_ = "Current Requests:"
public const val CREATE_ACCOUNT_ = "Create Account"
public const val SELECT_TIME = "Select time"
public const val ADD_NOTES = "Add notes"
public const val DROP_OFF_LOCATION = "Drop-off Location"
