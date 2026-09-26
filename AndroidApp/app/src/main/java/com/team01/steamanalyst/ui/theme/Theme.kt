package com.team01.steamanalyst.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.team01.steamanalyst.ui.theme.DarkOrange

val DarkColorScheme = darkColorScheme(
    background = BgRoot,
    surface = BgPanel,
    surfaceVariant = BgInput,
    surfaceDim = TextMuted,
    primaryContainer = BgCard,
    primary = AccentBlue,
    secondary = AccentPurple,
    onBackground = TextPrimary,
    onSurface = TextSecondary,
    tertiary = PositiveGreen,
    error = NegativeRed

)

val LightColorScheme = lightColorScheme(
    background = BgRootL,
    surface = BgPanelL,
    surfaceVariant = BgInputL,
    surfaceDim = TextMutedL,
    primaryContainer = BgCardL,
    primary = Purple40,
    secondary = PurpleGrey40,
    onBackground = TextPrimaryL,
    onSurface = TextSecondaryL,
    tertiary = DarkOrange,
    error = NegativeRed

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun SteamAnalystTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SMTypography,
        content = content
    )
}