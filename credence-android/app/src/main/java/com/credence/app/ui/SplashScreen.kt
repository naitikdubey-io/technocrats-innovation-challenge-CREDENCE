package com.credence.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.credence.app.R // Make sure this matches your package name!
import com.credence.app.ui.theme.DeepEspresso


import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // Auto-navigate to Onboarding after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screen.Onboarding.route) {
            // This prevents the user from hitting the "back" button to return to the splash screen
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Uses WarmIvory from your theme
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // The Cree Character
        Image(
            painter = painterResource(id = R.drawable.cree_character),
            contentDescription = "Cree Character",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // The Credence Logo Text
        Text(
            text = "credence",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp,
                fontSize = 28.sp
            ),
            color = DeepEspresso
        )
    }
}

