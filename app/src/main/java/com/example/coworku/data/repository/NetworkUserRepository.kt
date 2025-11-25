package com.example.coworku.data.repository

import android.util.Log
import com.example.coworku.data.remote.RetrofitClient
import com.example.coworku.domain.model.LoginRequest
import com.example.coworku.domain.model.RegisterRequest
import com.example.coworku.domain.model.User

class NetworkUserRepository {
    private val api = RetrofitClient.api

    suspend fun login(email: String, pass: String): User? {
        return try {
            val request = LoginRequest(email, pass)
            val userResponse = api.loginUser(request)

            User(
                id = userResponse.id,
                name = userResponse.name,
                email = userResponse.email,
                career = userResponse.career,
                skills = emptyList(),
                passwordHash = ""
            )
        } catch (e: Exception) {
            Log.e("LOGIN_ERROR", "Error al iniciar sesión: ${e.message}")
            null
        }
    }

    suspend fun register(firstName: String, lastName: String, email: String, pass: String): Boolean {
        return try {
            val request = RegisterRequest(firstName, lastName, email, pass)
            val response = api.registerUser(request)
            Log.d("REGISTER_SUCCESS", "Registro exitoso: ${response.message}")
            true
        } catch (e: Exception) {
            Log.e("REGISTER_ERROR", "Error al registrar: ${e.message}")
            false
        }
    }
}