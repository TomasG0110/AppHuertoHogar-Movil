package cl.duoc.dsy.huertohogar.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.dsy.huertohogar.uiScreen.LoginScreen
import cl.duoc.dsy.huertohogar.uiScreen.MainScreen
import cl.duoc.dsy.huertohogar.uiScreen.RegisterScreen
import cl.duoc.dsy.huertohogar.uiScreen.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route // La app parte en el Splash
    ) {
        // Ruta para la pantalla Splash
        composable(route = AppScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        // Ruta para la pantalla de Login
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        // Ruta para la pantalla de Registro
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }

        // Ruta para la pantalla Principal (que tendrá su propia nav)
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController = navController)
        }
    }
}