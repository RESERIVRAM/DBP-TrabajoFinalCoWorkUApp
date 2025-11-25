package com.example.coworku.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val passwordHash: String, // Or use a more secure way to store passwords
    val career: String,
    val skills: List<Skill>
)
