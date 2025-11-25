package com.example.coworku.data.repository

import android.util.Log
import com.example.coworku.data.remote.AuthApi
import com.example.coworku.data.remote.RetrofitClient
import com.example.coworku.domain.model.LoginRequest
import com.example.coworku.domain.model.RegisterRequest
import com.example.coworku.domain.model.User

class NetworkUserRepository {
    private val authApi: AuthApi by lazy {
        RetrofitClient.retrofit.create(AuthApi::class.java)
    }

    suspend fun login(email: String, pass: String): User? {
        return try {
            val request = LoginRequest(email, pass)
            // The API returns a UserResponse object
            val userResponse = authApi.loginUser(request)

            // Safely map the API response to the app's internal User model
            // We provide default values in case some fields are null in the response
            User(
                id = userResponse.id ?: "", // Provide default empty string if id is null
                name = userResponse.name ?: "Nombre no disponible",
                email = userResponse.email ?: email, // Fallback to the email used for login
                career = userResponse.career ?: "Carrera no especificada",
                skills = userResponse.skills ?: emptyList(), // Default to an empty list
                passwordHash = "" // This field is not needed after login, but the model requires it
            )
        } catch (e: Exception) {
            Log.e("LOGIN_ERROR", "Error al iniciar sesión: ${e.message}")
            null
        }
    }

    suspend fun register(firstName: String, lastName: String, email: String, pass: String): Boolean {
        return try {
            val request = RegisterRequest(firstName, lastName, email, pass)
            authApi.registerUser(request)
            true
        } catch (e: Exception) {
            Log.e("REGISTER_ERROR", "Error al registrar: ${e.message}")
            false
        }
    }
}
