package com.example.ridesharinghc.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ridesharinghc.ui.theme.RideSharingHCTheme
import com.example.ridesharinghc.ui.theme.SoftBlue
import androidx.compose.ui.text.font.FontWeight
import com.example.ridesharinghc.R

class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RideSharingHCTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: handle menu click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.menu_three_lines),
                    contentDescription = "Menu",
                    modifier = Modifier.size(42.dp)
                )
            }

            IconButton(onClick = { /* TODO: handle profile click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Profile",
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 130.dp)
        ) {
            Text(
                text = "RideSharingHC",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ActionBox(
                    painterResource(id = R.drawable.ic_get_ride),
                    text = "Get a Ride"
                )
                ActionBox(
                    painterResource(id = R.drawable.ic_offer_ride),
                    text = "Offer a Ride"
                )
            }

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
                    RequestItem("Walmart", "2:00 PM") // just static for now
                    RequestItem("TJ Maxx", "5:30 PM")
                }
            }
        }
    }
}

@Composable
fun ActionBox(icon: Painter, text: String) {
    Column(
        modifier = Modifier
            .size(150.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun RequestItem(location: String, time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Person",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = location, fontSize = 16.sp)
        }
        Text(text = time, fontSize = 16.sp)
    }
}


