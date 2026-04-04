package com.credence.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat



// We map our custom colors to Material 3 roles so standard Compose components adapt automatically
private val CredenceColorScheme = lightColorScheme(
    primary = BlushPink,
    onPrimary = Color.White,
    secondary = DeepRose,
    onSecondary = Color.White,
    tertiary = RoseGold,
    background = WarmIvory,
    onBackground = DeepEspresso,
    surface = Color.White, // Standard card backgrounds
    onSurface = DeepEspresso,
    surfaceVariant = SoftCream, // Used for pills and AI bubbles
    onSurfaceVariant = SoftMushroom,
    error = DeepRose,
    onError = Color.White
)

@Composable
fun CredenceTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // This makes the top status bar (where the battery/time is) match our Warm Ivory background
            window.statusBarColor = WarmIvory.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = CredenceColorScheme,
        typography = Typography,
        content = content
    )
}