package cl.duoc.dsy.huertohogar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// IE 2.3.1: Entidad de Room para persistencia local
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String,
    val nombre: String,
    val precioPorKilo: Double,
    val imagenResId: Int,
    var quantity: Int
)