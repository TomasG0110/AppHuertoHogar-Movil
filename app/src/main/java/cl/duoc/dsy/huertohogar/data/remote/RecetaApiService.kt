package cl.duoc.dsy.huertohogar.data.remote

import cl.duoc.dsy.huertohogar.model.RecetaResponse
import retrofit2.http.GET

interface RecetaApiService {
    // Solicitamos recetas vegetarianas
    @GET("api/json/v1/1/filter.php?c=Vegetarian")
    suspend fun getRecetas(): RecetaResponse
}