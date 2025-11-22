package cl.duoc.dsy.huertohogar.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecetaClient {
    private const val BASE_URL = "https://www.themealdb.com/"

    val service: RecetaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecetaApiService::class.java)
    }
}