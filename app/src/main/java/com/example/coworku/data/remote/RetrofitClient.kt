package com.example.coworku.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // IMPORTANTE: Usa el puerto que te funcionó en el navegador (5270)
    // 10.0.2.2 significa "mi computadora" desde el emulador
    private const val BASE_URL = "http://10.0.2.2:5270/"

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Para leer las fechas de C#
        .create()

    val api: CoWorkUApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CoWorkUApi::class.java)
    }
}