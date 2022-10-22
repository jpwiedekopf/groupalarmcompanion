package net.wiedekopf.groupalarmcompanion.ui.theme

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
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
)

@Composable
fun GroupAlarmCompanionTheme(
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            //ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

class CustomColor(
    val color: Color,
    val onColor: Color,
    val container: Color,
    val onContainer: Color
)

@Composable
fun getAvailableColors(): CustomColor = when (isSystemInDarkTheme()) {
    true -> CustomColor(
        color = dark_available,
        onColor = dark_onavailable,
        container = dark_availableContainer,
        onContainer = dark_onavailableContainer
    )
    false -> CustomColor(
        color = light_available,
        onColor = light_onavailable,
        container = light_availableContainer,
        onContainer = light_onavailableContainer
    )
}

@Composable
fun getUnavailableColors(): CustomColor = when (isSystemInDarkTheme()) {
    true -> CustomColor(
        color = dark_unavailable,
        onColor = dark_onunavailable,
        container = dark_unavailableContainer,
        onContainer = dark_onunavailableContainer
    )
    false -> CustomColor(
        color = light_unavailable,
        onColor = light_onunavailable,
        container = light_unavailableContainer,
        onContainer = light_onunavailableContainer
    )
}