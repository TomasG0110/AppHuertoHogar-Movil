package cl.duoc.dsy.huertohogar.model

import cl.duoc.dsy.huertohogar.navegacion.BottomNavScreen

abstract class Usuario (
    val nombre: String,
    val email: String,
    val rol: String
){
    //METODO POLIMORFICO
    abstract fun obtenerMenuNavegacion(): List<BottomNavScreen>

    fun obtenerSaludo(): String {
        return "Hola $nombre ($rol)"
    }
}

class Administrador(nombre: String, email: String) : Usuario(nombre, email, "Admin"){
    override fun obtenerMenuNavegacion(): List<BottomNavScreen> {
        return listOf(
            BottomNavScreen.Home,
            BottomNavScreen.Cart,
            BottomNavScreen.Profile,
            BottomNavScreen.Stores

        )
    }
}

class Cliente(nombre: String, email: String) : Usuario(nombre, email, "Cliente"){
    override fun obtenerMenuNavegacion(): List<BottomNavScreen> {
        return listOf(
            BottomNavScreen.Home,
            BottomNavScreen.Cart,
            BottomNavScreen.Recetas,
            BottomNavScreen.Profile
        )
    }
}

class Repartidor(nombre: String, email: String) : Usuario(nombre, email, "Repartidor"){
    override fun obtenerMenuNavegacion(): List<BottomNavScreen> {
        return listOf(
            BottomNavScreen.Home,
            BottomNavScreen.Stores,
            BottomNavScreen.Profile,
        )
    }
}

class Invitado: Usuario("Invitado", "","Visita"){
    override fun obtenerMenuNavegacion(): List<BottomNavScreen> {
        return listOf(
            BottomNavScreen.Home,
            BottomNavScreen.Profile
        )
    }
}




