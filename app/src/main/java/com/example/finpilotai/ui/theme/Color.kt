package com.example.finpilotai.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

internal val LightColors = lightColorScheme(
    primary = Color(0xFF0B6E66),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFA6F2E6),
    onPrimaryContainer = Color(0xFF00201D),
    secondary = Color(0xFF4A6360),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCE8E3),
    onSecondaryContainer = Color(0xFF051F1D),
    tertiary = Color(0xFF3F5BA9),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDAE2FF),
    onTertiaryContainer = Color(0xFF001848),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF5FAF8),
    onBackground = Color(0xFF171D1C),
    surface = Color(0xFFF5FAF8),
    onSurface = Color(0xFF171D1C),
    surfaceVariant = Color(0xFFDAE5E2),
    onSurfaceVariant = Color(0xFF3F4947),
    outline = Color(0xFF6F7977),
    outlineVariant = Color(0xFFBEC9C6),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFEFF5F3),
    surfaceContainer = Color(0xFFE9EFED),
    surfaceContainerHigh = Color(0xFFE3EAE7),
    surfaceContainerHighest = Color(0xFFDEE4E2),
)

internal val DarkColors = darkColorScheme(
    primary = Color(0xFF84D6CB),
    onPrimary = Color(0xFF003733),
    primaryContainer = Color(0xFF00504A),
    onPrimaryContainer = Color(0xFFA6F2E6),
    secondary = Color(0xFFB1CCC7),
    onSecondary = Color(0xFF1C3532),
    secondaryContainer = Color(0xFF334B48),
    onSecondaryContainer = Color(0xFFCCE8E3),
    tertiary = Color(0xFFB3C5FF),
    onTertiary = Color(0xFF0A2C77),
    tertiaryContainer = Color(0xFF26438F),
    onTertiaryContainer = Color(0xFFDAE2FF),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0E1514),
    onBackground = Color(0xFFDEE4E2),
    surface = Color(0xFF0E1514),
    onSurface = Color(0xFFDEE4E2),
    surfaceVariant = Color(0xFF3F4947),
    onSurfaceVariant = Color(0xFFBEC9C6),
    outline = Color(0xFF899391),
    outlineVariant = Color(0xFF3F4947),
    surfaceContainerLowest = Color(0xFF090F0E),
    surfaceContainerLow = Color(0xFF171D1C),
    surfaceContainer = Color(0xFF1B2120),
    surfaceContainerHigh = Color(0xFF252B2A),
    surfaceContainerHighest = Color(0xFF303635),
)

/** Finance specific semantic colors that Material's scheme does not cover. */
@Immutable
data class FinanceColors(
    val income: Color,
    val expense: Color,
    val warning: Color,
    val categoryPalette: List<Color>,
)

internal val LightFinanceColors = FinanceColors(
    income = Color(0xFF1B7F3B),
    expense = Color(0xFFB3261E),
    warning = Color(0xFF9A6100),
    categoryPalette = listOf(
        Color(0xFF0B6E66), Color(0xFF3F5BA9), Color(0xFFC2571A), Color(0xFF8E3D9B),
        Color(0xFF2E7D32), Color(0xFFB0306A), Color(0xFF00838F), Color(0xFF6D5E0F),
        Color(0xFF5C6BC0), Color(0xFF795548), Color(0xFF607D8B),
    ),
)

internal val DarkFinanceColors = FinanceColors(
    income = Color(0xFF7DDB94),
    expense = Color(0xFFFFB4AB),
    warning = Color(0xFFFFC56E),
    categoryPalette = listOf(
        Color(0xFF84D6CB), Color(0xFFB3C5FF), Color(0xFFFFB68E), Color(0xFFE7B3F0),
        Color(0xFF9CD8A0), Color(0xFFFFB0CC), Color(0xFF80DEEA), Color(0xFFE3CF6F),
        Color(0xFFC5CAE9), Color(0xFFD7B9A8), Color(0xFFB0BEC5),
    ),
)
