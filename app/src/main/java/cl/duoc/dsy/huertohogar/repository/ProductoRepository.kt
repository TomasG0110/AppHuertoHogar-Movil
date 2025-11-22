package cl.duoc.dsy.huertohogar.repository

import android.util.Log
import cl.duoc.dsy.huertohogar.data.remote.RetrofitClient
import cl.duoc.dsy.huertohogar.model.Producto

class ProductoRepository {

    // Instancia de la API (Retrofit)
    private val api = RetrofitClient.apiService

    // 1. Obtener productos del servidor (GET)
    suspend fun getProductos(): List<Producto> {
        return try {
            val response = api.getProductos()
            Log.d("ProductoRepo", "Productos obtenidos: ${response.size}")
            response
        } catch (e: Exception) {
            Log.e("ProductoRepo", "Error al obtener productos: ${e.message}")
            e.printStackTrace()
            emptyList() // Retorna lista vacía si falla para no cerrar la app
        }
    }

    // 2. Agregar producto al servidor (POST) - IE 3.1.3 CRUD
    suspend fun addProducto(producto: Producto): Producto? {
        return try {
            api.createProducto(producto)
        } catch (e: Exception) {
            Log.e("ProductoRepo", "Error al crear: ${e.message}")
            null
        }
    }

    // 3. Eliminar producto del servidor (DELETE) - IE 3.1.3 CRUD
    suspend fun deleteProducto(id: Long): Boolean {
        return try {
            val response = api.deleteProducto(id)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("ProductoRepo", "Error al eliminar: ${e.message}")
            false
        }
    }
}