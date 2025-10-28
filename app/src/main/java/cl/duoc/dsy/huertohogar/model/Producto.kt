package cl.duoc.dsy.huertohogar.model

import androidx.annotation.DrawableRes

// IE 2.3.1: Definición del Modelo de datos
data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precioPorKilo: Double,
    val stockEnKilos: Int,
    val categoria: String,
    @DrawableRes val imagenResId: Int // ID de la imagen en res/drawable
)