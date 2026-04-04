package com.credence.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.credence.app.R // MUST match your package name
import com.credence.app.ui.theme.*


@Composable
fun AuthScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmIvory) // Global Warm Ivory Background
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = DeepEspresso,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { navController.popBackStack() } // Goes back to Onboarding
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Secure Entry",
                style = MaterialTheme.typography.titleLarge,
                color = DeepEspresso
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes center content down


        Image(
            painter = painterResource(id = R.drawable.cree_character),
            contentDescription = "Cree Security",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your space. Your data.",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = DeepEspresso
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Access your private ledger securely.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = DeepEspresso.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.height(48.dp))


        Button(
            onClick = {
                // Simulating a successful login for the MVP.
                // Routes to Home and destroys the splash/auth backstack so the user can't hit 'back' to log out.
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(64.dp)
                // Adds the soft pink glowing shadow seen in your design
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(50), spotColor = BlushPink, ambientColor = BlushPink),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = SoftPeach, // Warm peach background
                contentColor = DeepEspresso
            ),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {



            Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                Image(painter= painterResource(R.drawable.google),null)
            }


            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Continue with Google",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                color = DeepEspresso
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Credence uses advance-grade encryption.",
            style = MaterialTheme.typography.bodyMedium,
            color = SoftMushroom,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1.5f)) // Balances the bottom spacing
    }
}

