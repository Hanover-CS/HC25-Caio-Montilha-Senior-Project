package com.example.ridesharinghc.composables.screens.common

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

/**
 * Composable function [LocationSearchBar] displays a search bar allowing users to select a drop-off location.
 *
 * @param dropOffLocation MutableState for the drop-off location selected by the user.
 * @param onLocationSelected Callback with the selected location coordinates and address.
 */
@Composable
fun LocationSearchBar(
    dropOffLocation: MutableState<String>,
    onLocationSelected: (LatLng, String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val place = Autocomplete.getPlaceFromIntent(data!!)
            place.latLng?.let { latLng ->
                dropOffLocation.value = place.address ?: ""
                onLocationSelected(latLng, place.address ?: "")
            }
        }
    }

    Button(
        onClick = {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(context)
            launcher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Search Drop-off Location")
    }
}

