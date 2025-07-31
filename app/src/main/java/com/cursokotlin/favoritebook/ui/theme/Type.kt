package com.cursokotlin.favoritebook.ui.theme

import com.cursokotlin.favoritebook.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val exile = FontFamily(
    Font(R.font.exile, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    // --- Headline (Para títulos de secciones, títulos principales de pantalla) ---
    headlineLarge = TextStyle( // Recomendado para títulos principales en pantallas de detalle
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle( // Alternativa para títulos principales o subtítulos muy grandes
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle( // Títulos de card grandes, o subtítulos principales
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // --- Title (Para subtítulos, títulos de componentes, títulos de cards internos) ---
    titleLarge = TextStyle( // Subtítulos importantes o títulos de secciones en detalle
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle( // Títulos dentro de cards, subtítulos de nivel medio
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium, // Material Design recomienda Medium para este.
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle( // Subtítulos más pequeños o texto de encabezado en componentes pequeños
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium, // Material Design recomienda Medium para este.
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // --- Body (Para el texto del cuerpo, contenido principal) ---
    bodyLarge = TextStyle( // Texto principal de cards y contenido extenso en detalle
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle( // Texto secundario, descripciones más cortas
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle( // Texto muy pequeño, notas, metadatos, captions
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // --- Label (Para etiquetas de UI, botones, chips, campos de entrada) ---
    labelLarge = TextStyle( // Texto en botones, etiquetas grandes
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium, // Material Design recomienda Medium para este.
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle( // Etiquetas en chips, campos de entrada, texto de menú
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium, // Material Design recomienda Medium para este.
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle( // Etiquetas muy pequeñas, metadatos, texto de error
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium, // Material Design recomienda Medium para este.
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)