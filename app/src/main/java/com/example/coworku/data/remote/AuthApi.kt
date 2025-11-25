package com.example.coworku.data.remote

import com.example.coworku.domain.model.LoginRequest
import com.example.coworku.domain.model.RegisterRequest
import com.example.coworku.domain.model.RegisterResponse
import com.example.coworku.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/Auth/Login")
    suspend fun loginUser(@Body request: LoginRequest): User

    @POST("api/Auth/Register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse
}
