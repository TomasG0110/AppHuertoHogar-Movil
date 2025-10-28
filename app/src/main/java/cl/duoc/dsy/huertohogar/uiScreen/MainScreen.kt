package cl.duoc.dsy.huertohogar.uiScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.duoc.dsy.huertohogar.navegacion.BottomNavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    // Este es el NavController INTERNO (para el Bottom Nav Bar)
    val mainNavController = rememberNavController()

    // Lista de pantallas del Bottom Nav Bar
    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Cart,
        BottomNavScreen.Profile
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HuertoHogar") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Verde
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Blanco
                )
            )
        },
        bottomBar = {
            // --- Bottom Navigation Bar ---
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                // Obtener la ruta actual para saber qué ícono resaltar
                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            mainNavController.navigate(screen.route) {
                                // Navegación de ítem único
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // --- Contenido Principal (NavHost Anidado) ---
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = mainNavController,
                startDestination = BottomNavScreen.Home.route // Empezar en Home
            ) {
                composable(BottomNavScreen.Home.route) { HomeScreen() }
                composable(BottomNavScreen.Cart.route) { CartScreen() }
                composable(BottomNavScreen.Profile.route) { ProfileScreen() }
            }
        }
    }
}