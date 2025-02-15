package com.example.ridesharinghc.composables.screens.HomeScreen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.ridesharinghc.data.constants.Prompts.ARE_YOU_SURE_YOU_WANT_TO_ACCEPT_THIS_REQUEST
import com.example.ridesharinghc.data.constants.Labels.CONFIRMATION
import com.example.ridesharinghc.data.constants

/**
 * Displays a confirmation dialog for accepting a ride request or offer.
 *
 * @param onConfirm Lambda function called when the user confirms the action.
 * @param onDismiss Lambda function called when the user dismisses the dialog.
 */
@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = CONFIRMATION) },
        text = { Text(constants.Prompts.ARE_YOU_SURE_YOU_WANT_TO_ACCEPT_THIS_REQUEST) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}
