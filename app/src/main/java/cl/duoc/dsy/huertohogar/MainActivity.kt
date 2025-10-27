package cl.duoc.dsy.huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cl.duoc.dsy.huertohogar.navegacion.AppNavigation
import cl.duoc.dsy.huertohogar.ui.theme.HuertoHogarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HuertoHogarTheme {
                // Aquí llamamos a nuestro sistema de navegación
                AppNavigation()
            }
        }
    }
}