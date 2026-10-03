package com.suscorp.acta.ui.theme

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple1,
    secondary = Blue1,
    tertiary = Purple3
)

private val LightColorScheme = lightColorScheme(
    primary = Purple1,
    secondary = Blue1,
    tertiary = Purple3
)

@Composable
fun ActaTheme(
    themeChoice: String,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
    context: Context,
) {

    var finalTheme:Boolean

    if(themeChoice != "none"){
        if(themeChoice == "dark") {
            finalTheme = true;
        }else{
            finalTheme = false;
        }
    }
    else{
        finalTheme = darkTheme
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (finalTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}