package com.credence.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.credence.app.ui.AuthScreen
import com.credence.app.ui.ChatScreen
import com.credence.app.ui.HomeScreen
import com.credence.app.ui.OnboardingScreen
import com.credence.app.ui.Screen
import com.credence.app.ui.SplashScreen
import com.credence.app.ui.TimelineScreen
import com.credence.app.viewmodel.MainViewModel

import com.credence.app.ui.theme.CredenceTheme
import com.credence.app.ui.theme.DeepEspresso
import com.credence.app.ui.theme.SoftMushroom


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Wrap the app in your custom Design System
            CredenceTheme {
                // 2. Instantiate the Brain
                val viewModel: MainViewModel = viewModel()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CredenceApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun CredenceApp(viewModel: MainViewModel) {
    val navController = rememberNavController()

    // Track current route to determine Bottom Bar visibility
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Only show bottom navigation on these three core screens
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Timeline.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                CredenceBottomNavigation(navController = navController, currentRoute = currentRoute)
            }
        }
    ) { innerPadding ->
        // The True Navigation Map
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) { SplashScreen(navController) }
            composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
            composable(Screen.Auth.route) { AuthScreen(navController) }
            composable(Screen.Home.route) { HomeScreen(navController, viewModel) }
            composable(Screen.Chat.route) { ChatScreen(navController, viewModel) }
            composable(Screen.Timeline.route) { TimelineScreen(navController, viewModel) }
            composable(Screen.ShareQR.route) { ShareQRScreen(navController, viewModel) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
        }
    }
}


@Composable
fun CredenceBottomNavigation(navController: NavHostController, currentRoute: String?) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        // Home Tab
        NavigationBarItem(
            icon = { Text("🏠") },
            label = { Text("Home", style = MaterialTheme.typography.labelMedium) },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = DeepEspresso,
                unselectedIconColor = SoftMushroom,
                selectedTextColor = DeepEspresso,
                unselectedTextColor = SoftMushroom,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
        // Ledger Tab
        NavigationBarItem(
            icon = { Text("📋") },
            label = { Text("Ledger", style = MaterialTheme.typography.labelMedium) },
            selected = currentRoute == Screen.Timeline.route,
            onClick = {
                if (currentRoute != Screen.Timeline.route) {
                    navController.navigate(Screen.Timeline.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = DeepEspresso,
                unselectedIconColor = SoftMushroom,
                selectedTextColor = DeepEspresso,
                unselectedTextColor = SoftMushroom,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
        // Profile Tab
        NavigationBarItem(
            icon = { Text("👤") },
            label = { Text("Profile", style = MaterialTheme.typography.labelMedium) },
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                if (currentRoute != Screen.Profile.route) {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = DeepEspresso,
                unselectedIconColor = SoftMushroom,
                selectedTextColor = DeepEspresso,
                unselectedTextColor = SoftMushroom,
                indicatorColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}







@Composable fun ShareQRScreen(navController: NavHostController, viewModel: MainViewModel) {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        Text("Zero-Trust QR Gate")
        Button(onClick = { navController.popBackStack() }) { Text("Close") }
    }
}
@Composable fun ProfileScreen(navController: NavHostController) {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) { Text("User Profile") }
}