package com.credence.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.credence.app.R

import com.yourcompany.credence.ui.theme.DeepEspresso
import com.yourcompany.credence.ui.theme.SoftMushroom


// 1. Declare Font Families
val Outfit = FontFamily(
    Font(R.font.outfit_medium, FontWeight.Medium),
    Font(R.font.outfit_semibold, FontWeight.SemiBold),
    Font(R.font.outfit_bold, FontWeight.Bold)
)

val DMSans = FontFamily(
    Font(R.font.dmsans_regular, FontWeight.Normal),
    Font(R.font.dmsans_medium, FontWeight.Medium),
    Font(R.font.dmsans_bold, FontWeight.Bold)
)

// 2. Map to Material 3 Typography
val Typography = Typography(
    // Used for large Screen Headers ("How are you feeling today?")
    headlineLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        color = DeepEspresso
    ),
    // Used for Primary Button Text ("Talk to Cree")
    headlineMedium = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.White
    ),
    // Used for Subheaders / Security Titles
    titleLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = DeepEspresso
    ),
    // Used for User Profile Name / Card Titles
    titleMedium = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = DeepEspresso
    ),
    // Used for standard AI and User Chat text
    bodyLarge = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Normal, // Normal for AI, Medium for User
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = DeepEspresso
    ),
    // Used for Card body text / Subtext
    bodyMedium = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = SoftMushroom
    ),
    // Used for Timestamps, Biometric pills, small labels
    labelMedium = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = SoftMushroom
    )
)