package cl.duoc.dsy.huertohogar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// IE 2.3.1: Entidad de Room para persistencia local
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Long,
    val nombre: String,
    val precio: Int,
    val imagenNombre: String,
    var quantity: Int
)