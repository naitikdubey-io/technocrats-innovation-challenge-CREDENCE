package com.credence.app.ui

// The True Navigation Map based on UI Designs
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Chat : Screen("chat")
    object Timeline : Screen("timeline")
    object ShareQR : Screen("share_qr")
    object Profile : Screen("profile")
}