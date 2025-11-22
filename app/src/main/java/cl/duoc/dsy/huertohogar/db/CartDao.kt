package cl.duoc.dsy.huertohogar.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cl.duoc.dsy.huertohogar.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // Inserta un ítem. Si ya existe, lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItem)

    // Actualiza un ítem (ej. para cambiar cantidad)
    @Update
    suspend fun updateItem(item: CartItem)

    // Obtiene todos los ítems del carrito como un Flow (se actualiza solo)
    @Query("SELECT * FROM cart_items")
    fun getAllItems(): Flow<List<CartItem>>

    // Obtiene un ítem específico por su ID de producto
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getItemByProductId(productId: String): CartItem?

    // Elimina un ítem de la base de datos
    @Query("DELETE FROM cart_items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int)

    // Limpia todo el carrito
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}