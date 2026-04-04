package com.credence.app.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.credence.app.R
import com.credence.app.ui.theme.BlushPink
import com.credence.app.ui.theme.DeepEspresso
import com.credence.app.ui.theme.SoftPeach
import com.credence.app.ui.theme.WarmIvory


@Composable
fun OnboardingScreen(navController: NavHostController) {
    // State to track which onboarding page we are on (0 or 1)
    var currentPage by remember { mutableStateOf(0) }

    // Premium soft gradient matching your design image
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(WarmIvory, SoftPeach, BlushPink.copy(alpha = 0.3f))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Decorative background elements (the soft white clovers/blobs in your design)
        // For the MVP, we simulate the vibe with a clean gradient, but we can add vectors later!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // The Cree Character
            Image(
                painter = painterResource(id = R.drawable.cree_character),
                contentDescription = "Cree Character",
                modifier = Modifier.size(320.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Smooth crossfade animation between the text steps
            Crossfade(
                targetState = currentPage,
                animationSpec = tween(500),
                label = "Onboarding Crossfade"
            ) { page ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (page == 0) {
                        Text(
                            text = "We care how\nyou feel!",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "Talk naturally.\nWe translate.",
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Just describe how you feel.\nOur AI turns your words into\nclinical-grade reports doctors\nactually read.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = DeepEspresso.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // The Custom Pill Button (Matches your UI image)
            Button(
                onClick = {
                    if (currentPage == 0) {
                        currentPage = 1 // Go to next page
                    } else {
                        // Navigate to Auth Screen and clear backstack
                        navController.navigate(Screen.Auth.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = WarmIvory),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = if (currentPage == 0) "Meet Cree" else "Get Started",
                    style = MaterialTheme.typography.titleMedium,
                    color = DeepEspresso
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Black circular arrow icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(DeepEspresso),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        tint = WarmIvory,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun prev(){
    OnboardingScreen(rememberNavController())
}