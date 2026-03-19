package com.example.saludapp.ui.theme

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

// Colores personalizados
private val PrimaryPurple = Color(0xFF6B4EFF) // Morado del logo
private val LightBackground = Color(0xFFF8F9FE) // Lila muy claro
private val DarkBackground = Color(0xFF121212) // Gris casi negro
private val DarkSurface = Color(0xFF1E1E2E) // Gris azulado para las tarjetas

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    secondary = PrimaryPurple,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFAAAABB)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    secondary = PrimaryPurple,
    background = LightBackground,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF1A1A24),
    onSurface = Color(0xFF1A1A24),
    onSurfaceVariant = Color.Gray
)

@Composable
fun SaludAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}