package com.example.coworku.domain.model

import com.google.gson.annotations.SerializedName

// MODELO PARA LOGIN
data class LoginRequest(
    @SerializedName("Email") val email: String,
    @SerializedName("Password") val password: String
)

// RESPUESTA DEL LOGIN
data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val career: String,
    val message: String
)

// MODELO PARA REGISTRO
data class RegisterRequest(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Password") val password: String
)

// RESPUESTA DEL REGISTRO
data class RegisterResponse(
    val userId: String,
    val message: String
)
