package cl.duoc.dsy.huertohogar.ui.theme

import androidx.compose.ui.graphics.Color



// Color de fondo principal [cite: 151]
val BlancoSuave = Color(0xFFF7F7F7)

// Colores de acento [cite: 154-156]
val VerdeEsmeralda = Color(0xFF2E8B57) // Botones, enlaces
val AmarilloMostaza = Color(0xFFFFD700) // Ofertas
val MarronClaro = Color(0xFF8B4513) // Títulos

// Colores de Texto [cite: 168, 170]
val GrisOscuro = Color(0xFF333333) // Texto principal
val GrisMedio = Color(0xFF666666) // Texto secundario

// --- Mapeo a Material 3 Theme ---

// Paleta Light
val md_theme_light_primary = VerdeEsmeralda
val md_theme_light_onPrimary = Color.White
val md_theme_light_primaryContainer = Color(0xFFB4F3D0)
val md_theme_light_onPrimaryContainer = Color(0xFF002111)

val md_theme_light_secondary = AmarilloMostaza
val md_theme_light_onSecondary = Color.Black
val md_theme_light_secondaryContainer = Color(0xFFFFE08B)
val md_theme_light_onSecondaryContainer = Color(0xFF2A1C00)

val md_theme_light_tertiary = MarronClaro
val md_theme_light_onTertiary = Color.White
val md_theme_light_tertiaryContainer = Color(0xFFFFDBCF)
val md_theme_light_onTertiaryContainer = Color(0xFF380C00)

val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_onError = Color.White

val md_theme_light_background = BlancoSuave
val md_theme_light_onBackground = GrisOscuro

val md_theme_light_surface = BlancoSuave
val md_theme_light_onSurface = GrisOscuro

val md_theme_light_surfaceVariant = Color(0xFFDEE5DD)
val md_theme_light_onSurfaceVariant = GrisMedio
val md_theme_light_outline = Color(0xFF727972)

// Paleta Dark (Opcional, pero bueno tenerla definida)
val md_theme_dark_primary = Color(0xFF98D6B5)
val md_theme_dark_onPrimary = Color(0xFF003820)
val md_theme_dark_primaryContainer = Color(0xFF005131)
val md_theme_dark_onPrimaryContainer = Color(0xFFB4F3D0)

val md_theme_dark_secondary = Color(0xFFEBC100)
val md_theme_dark_onSecondary = Color(0xFF463000)
val md_theme_dark_secondaryContainer = Color(0xFF644700)
val md_theme_dark_onSecondaryContainer = Color(0xFFFFE08B)

val md_theme_dark_tertiary = Color(0xFFFFB59A)
val md_theme_dark_onTertiary = Color(0xFF5B1B00)
val md_theme_dark_tertiaryContainer = Color(0xFF7D2F17)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFDBCF)
// ... (puedes completar el resto si es necesario)