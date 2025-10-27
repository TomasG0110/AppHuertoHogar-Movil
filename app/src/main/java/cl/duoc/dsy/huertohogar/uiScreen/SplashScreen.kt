package cl.duoc.dsy.huertohogar.uiScreen


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.duoc.dsy.huertohogar.R // Importante: que sea la R de tu proyecto
import cl.duoc.dsy.huertohogar.navegacion.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Variable 'remember' para controlar la animación de escala (zoom)
    val scale = remember {
        Animatable(0.5f) // Empezar a la mitad del tamaño
    }

    // LaunchedEffect se ejecuta una sola vez cuando la pantalla aparece
    LaunchedEffect(key1 = true) {
        // Inicia la animación de escala (zoom in)
        scale.animateTo(
            targetValue = 1f, // Terminar al 100% del tamaño
            animationSpec = tween(durationMillis = 1500) // Duración de la animación
        )

        // Espera un momento después de la animación
        delay(1000L)

        // Navega al Login y elimina el Splash Screen del historial
        navController.navigate(AppScreens.LoginScreen.route) {
            popUpTo(AppScreens.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    // --- Interfaz Visual ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Usamos el color de fondo definido en el Tema (BlancoSuave)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.Logo_HuertoHogar),
            contentDescription = "Logo HuertoHogar",
            modifier = Modifier
                .size(250.dp)
                .scale(scale.value) // Aplica la escala animada
        )
    }
}