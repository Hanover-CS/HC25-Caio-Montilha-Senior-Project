package com.example.ridesharinghc.composables.screens.UserProfileScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Composable function [UserProfileTextField] provides a customizable text field
 * for displaying and editing profile data. Supports various keyboard types and
 * can be enabled or disabled.
 *
 * @param value The current text value of the field.
 * @param onValueChange Lambda function to update the field's value.
 * @param label The label to display as a hint inside the text field.
 * @param isPassword Boolean to determine if the field is for password input (default is false).
 * @param keyboardType The type of keyboard to show (default is [KeyboardType.Text]).
 * @param isEnabled Boolean to determine if the field is editable (default is true).
 */
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