package com.example.ridesharinghc.composables.screens.HomeScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.R
import com.example.ridesharinghc.utils.deleteRequest
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

/**
 * Composable function [RideRequestCard] that displays the current ride requests.
 */
@Composable
fun RideRequestCard(
    requests: List<Pair<String, Map<String, String>>>,
    currentUserId: String?,
    setSelectedRequest: (Pair<String, Map<String, String>>?) -> Unit,
    setShowDialog: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Current Requests:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            requests.forEach { (key, request) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${request["dropOffLocation"]} - ${request["time"]}",
                        fontSize = 16.sp
                    )
                    Row {
                        if (request["userId"] == currentUserId) {
                            IconButton(onClick = { deleteRequest(key) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                setSelectedRequest(key to request)
                                setShowDialog(true)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_check),
                                    contentDescription = "Accept",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}