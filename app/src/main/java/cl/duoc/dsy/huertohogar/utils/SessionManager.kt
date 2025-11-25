package cl.duoc.dsy.huertohogar.utils

import cl.duoc.dsy.huertohogar.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SessionManager {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual = _usuarioActual.asStateFlow()

    //Funcion para simular el login con diferentes roles
    fun iniciarSesion(tipo: String){
        val usuario = when(tipo){
            "Admin" -> Administrador("Tomas Admin", "admin@huerto.cl")
            "Cliente" -> Cliente("Tomas Cliente", "cliente@huerto.cl")
            "Repartidor" -> Repartidor("Juan Reparto", "delivey@huerto.cl")
             else -> Invitado()
        }
        _usuarioActual.value = usuario
    }
    fun cerrarSesion(){
        _usuarioActual.value = null
    }
}
