package cl.duoc.dsy.huertohogar.repository

import cl.duoc.dsy.huertohogar.db.CartDao
import cl.duoc.dsy.huertohogar.model.CartItem
import cl.duoc.dsy.huertohogar.model.Producto
import kotlinx.coroutines.flow.Flow

// IE 2.3.1: Repositorio para la lógica de persistencia del carrito
class CarritoRepository(private val cartDao: CartDao) {

    // Obtiene todos los ítems del carrito (para la pantalla Carrito)
    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllItems()
    }

    // Lógica para "Añadir al Carrito"
    suspend fun addProductoToCart(producto: Producto) {

        // 1. Revisar si el producto ya existe en el carrito
        val existingItem = cartDao.getItemByProductId(producto.id)

        if (existingItem != null) {
            // 2. Si existe, solo aumenta la cantidad
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            cartDao.updateItem(updatedItem)
        } else {
            // 3. Si no existe, crea un nuevo CartItem
            val newItem = CartItem(
                productId = producto.id,
                nombre = producto.nombre,
                precioPorKilo = producto.precioPorKilo,
                imagenResId = producto.imagenResId,
                quantity = 1 // Cantidad inicial
            )
            cartDao.insertItem(newItem)
        }
    }
    suspend fun deleteItemFromCart(item: CartItem) {
        // El DAO espera el ID del ítem a borrar
        cartDao.deleteItem(item.id)
    }


}