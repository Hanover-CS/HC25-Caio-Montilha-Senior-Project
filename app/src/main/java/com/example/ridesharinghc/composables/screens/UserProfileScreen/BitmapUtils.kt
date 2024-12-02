package com.example.ridesharinghc.composables.screens.UserProfileScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

/**
 * Decodes a Base64-encoded string into a [Bitmap].
 *
 * This function converts a Base64-encoded string into a [Bitmap] object.
 * It uses Android's [Base64] class to decode the input string and
 * [BitmapFactory.decodeByteArray] to convert the resulting byte array into a [Bitmap].
 * If the input string is null or invalid, it returns `null`.
 *
 * @param base64Str The Base64-encoded string representing image data.
 * @return A [Bitmap] object if decoding is successful, or `null` if the input is invalid.
 */
fun decodeBase64ToBitmap(base64Str: String?): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        null
    }
}
