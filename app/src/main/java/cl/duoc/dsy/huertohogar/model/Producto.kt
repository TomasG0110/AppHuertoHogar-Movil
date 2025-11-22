package cl.duoc.dsy.huertohogar.model

import androidx.annotation.DrawableRes

// IE 2.3.1: Definición del Modelo de datos
data class Producto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagenNombre: String
)