package com.cursokotlin.favoritebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.cursokotlin.favoritebook.navigation.NavigationWrapper
import com.cursokotlin.favoritebook.ui.theme.FavoriteBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicar al sistema que el contenido de tu app se dibuje detrás de las barras del sistema.
        // Esto es esencial para poder controlar su color y apariencia.
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            FavoriteBookTheme {
                NavigationWrapper()
            }
        }
    }
}
