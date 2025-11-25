package com.example.coworku.data.remote

import com.example.coworku.domain.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoWorkUApi {
    // --- PROYECTOS ---
    @GET("api/ProjectsApi")
    suspend fun getProjects(): List<Project>

    @GET("api/ProjectsApi/{id}")
    suspend fun getProjectById(@Path("id") id: Int): Project

    // --- AUTENTICACIÓN ---
    // CAMBIO CRÍTICO: Ahora apuntan a "api/auth", coincidiendo con el cambio en C#

    @POST("api/auth/Login")
    suspend fun loginUser(@Body request: LoginRequest): UserResponse

    @POST("api/auth/Register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse
}