package cl.duoc.dsy.huertohogar.data.remote

import cl.duoc.dsy.huertohogar.model.Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService{

    // 1. Obtener lista (GET)
    @GET("api/productos")
    suspend fun getProductos(): List<Producto>

    // 2. Crear producto (POST)
    @POST("api/productos")
    suspend fun createProducto(@Body producto: Producto): Producto

    // 3. Actualizar producto (PUT)
    @PUT("api/productos/{id}")
    suspend fun updateProducto(@Path("id") id: Long, @Body producto: Producto): Producto

    // 4. Eliminar producto (DELETE)
    @DELETE("api/productos/{id}")
    suspend fun deleteProducto(@Path("id") id: Long): Response<Unit>
}

