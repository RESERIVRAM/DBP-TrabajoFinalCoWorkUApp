package com.example.coworku.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Usamos el puerto 5270 que configuramos para tu ASP.NET
    private const val BASE_URL = "http://10.0.2.2:5270/"

    // 1. Configuración de Gson para leer las fechas de SQL Server correctamente
    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    // 2. Variable 'api' pública que el Repositorio está buscando
    val api: CoWorkUApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Usamos el gson personalizado aquí
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CoWorkUApi::class.java)
    }
}