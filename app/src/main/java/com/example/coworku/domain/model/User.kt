package com.example.coworku.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val career: String,
    val skills: List<Skill>
)