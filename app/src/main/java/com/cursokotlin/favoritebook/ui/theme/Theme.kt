package com.cursokotlin.favoritebook.ui.theme

import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = primaryDarkColor,
    onPrimary = Color.White,
    background = Gray20,
    onBackground = Gray70,
    surface = Gray20,
    secondary = secondaryDarkColor,
    onSecondary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = Color.White,
    background = Gray100,
    onBackground = Gray80,
    surface = Gray100 ,
    secondary = secondaryColor,
    onSecondary = Color.White

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
fun FavoriteBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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

    // --- Lógica para el color de los íconos de la barra de estado ---
    val view = LocalView.current
    if (!view.isInEditMode) { // Evita la ejecución en el editor de previsualización
        val currentWindow = (view.context as Activity).window
        // Obtén el InsetsController para gestionar las barras del sistema
        val insetsController = WindowCompat.getInsetsController(currentWindow, view)

        // Define si los íconos de la barra de estado deben ser claros u oscuros.
        // Esto depende del color de fondo que hayas definido en themes.xml.
        // Si el fondo es CLARO (Light Theme), los íconos deben ser OSCUROS.
        // Si el fondo es OSCURO (Dark Theme), los íconos deben ser CLAROS.
        val useDarkIcons = !darkTheme

        SideEffect {
            // No establecemos el color de fondo de la barra de estado aquí directamente,
            // ya que eso se define en themes.xml.
            // Solo controlamos el color de los íconos para asegurar el contraste.
            insetsController.isAppearanceLightStatusBars = useDarkIcons

            // Opcional: También puedes controlar el color de los íconos de la barra de navegación
            // insetsController?.isAppearanceLightNavigationBars = useDarkIcons // o tu propia lógica
        }
    }
    // --- Fin de la lógica para la barra de estado ---


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}