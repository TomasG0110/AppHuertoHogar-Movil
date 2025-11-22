package cl.duoc.dsy.huertohogar.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Inicio",
        icon = Icons.Default.Home
    )

    object Cart : BottomNavScreen(
        route = "cart",
        title = "Carrito",
        icon = Icons.Default.ShoppingCart
    )

    object Profile : BottomNavScreen(
        route = "profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}