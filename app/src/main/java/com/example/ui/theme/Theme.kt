package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val VayoraLightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    onPrimary = DarkText,
    primaryContainer = LightYellowContainer,
    onPrimaryContainer = DarkText,
    secondary = DeepGolden,
    onSecondary = PureWhite,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = DarkText,
    tertiary = AmberAccent,
    onTertiary = PureWhite,
    background = SoftCreamBackground,
    onBackground = DarkText,
    surface = PureWhite,
    onSurface = DarkText,
    surfaceVariant = LightYellowContainer,
    onSurfaceVariant = DarkTextSecondary,
    outline = GoldBorder,
    outlineVariant = GoldBorder,
    error = ErrorRed,
    onError = PureWhite,
    errorContainer = ErrorRedBg,
    onErrorContainer = ErrorRed
)

private val VayoraDarkColorScheme = darkColorScheme(
    primary = PrimaryYellow,
    onPrimary = DarkText,
    primaryContainer = DeepGolden,
    onPrimaryContainer = SoftCreamBackground,
    secondary = DeepGolden,
    onSecondary = PureWhite,
    secondaryContainer = Color(0xFF451A03),
    onSecondaryContainer = PrimaryYellow,
    tertiary = LightYellowContainer,
    onTertiary = DarkText,
    background = Color(0xFF1C1917),
    onBackground = SoftCreamBackground,
    surface = Color(0xFF292524),
    onSurface = PureWhite,
    surfaceVariant = Color(0xFF44403C),
    onSurfaceVariant = SoftCreamBackground,
    outline = DeepGolden,
    outlineVariant = Color(0xFF78716C),
    error = Color(0xFFF87171),
    onError = PureWhite,
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Preserve custom yellow and golden brand scheme
    val colorScheme = if (darkTheme) VayoraDarkColorScheme else VayoraLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
