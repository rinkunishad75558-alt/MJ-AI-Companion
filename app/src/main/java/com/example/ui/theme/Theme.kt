package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SleekColorScheme = darkColorScheme(
    primary = SleekPrimary,
    secondary = SleekSecondary,
    background = SleekBackground,
    surface = SleekSurface,
    surfaceVariant = SleekSurfaceVariant,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = SleekTextPrimary,
    onSurface = SleekTextPrimary,
    onSurfaceVariant = SleekTextSecondary,
    outline = SleekBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    themeName: String = "Sleek Interface",
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = SleekColorScheme,
        typography = Typography,
        content = content
    )
}
