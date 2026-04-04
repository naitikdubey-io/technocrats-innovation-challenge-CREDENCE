package com.credence.app.ui

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.credence.app.R // MUST match your package name
import com.credence.app.ui.theme.*
import com.credence.app.viewmodel.MainViewModel


@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmIvory)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Circular Avatar Placeholder
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, SubtleGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = DeepEspresso,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Welcome back",
                        style = MaterialTheme.typography.labelMedium,
                        color = SoftMushroom
                    )
                    Text(
                        text = "Naina Dubey",
                        style = MaterialTheme.typography.titleMedium,
                        color = DeepEspresso
                    )
                }
            }

            // Vitals Synced Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(SoftCream)
                    .border(1.dp, BlushPink.copy(alpha = 0.3f), RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "\"Vitals Synced\"",
                    style = MaterialTheme.typography.labelMedium,
                    color = SoftMushroom,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Hello Naina! How are\nyou feeling today?\"",
            style = MaterialTheme.typography.headlineLarge,
            color = DeepEspresso,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Store ONLY the integer resource IDs in the list
            val moodDrawables = listOf(
                R.drawable.mood_happy,
                R.drawable.mood_bored,
                R.drawable.mood_sad,
                R.drawable.mood_crying,
                R.drawable.mood_angry
            )


            moodDrawables.forEach { drawableId ->
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { /* Future mood logging logic */ },
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))


        Box(contentAlignment = Alignment.Center) {
            // Simulated soft glow behind Cree
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(BlushPink.copy(alpha = 0.15f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )
            Image(
                painter = painterResource(id = R.drawable.cree_character),
                contentDescription = "Cree Character",
                modifier = Modifier.size(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))


        Button(
            onClick = { navController.navigate(Screen.Chat.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = BlushPink,
                    ambientColor = BlushPink
                ),
            shape = RoundedCornerShape(32.dp), // Extremely rounded corners to match design
            colors = ButtonDefaults.buttonColors(containerColor = BlushPink),
            contentPadding = PaddingValues(0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Talk to Cree",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Log symptoms, pain, or just vent. I'm\nlistening.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}