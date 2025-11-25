package com.example.coworku.data.repository.fake

import com.example.coworku.domain.model.User
import com.example.coworku.domain.model.Skill

class UserRepositoryFake {
    private val users = mutableListOf(
        User(
            id = "1",
            name = "Renato Riva",
            email = "renato.riva@example.com",
            passwordHash = "password123", // In a real app, use a proper hash
            career = "Ingeniería de Software",
            skills = listOf(
                Skill("Kotlin", "Avanzado"),
                Skill("Jetpack Compose", "Intermedio")
            )
        ),
        User(
            id = "2",
            name = "Maria Garcia",
            email = "maria.garcia@example.com",
            passwordHash = "securepassword", // In a real app, use a proper hash
            career = "Diseño UX/UI",
            skills = listOf(
                Skill("Figma", "Avanzado"),
                Skill("Investigación de Usuarios", "Intermedio")
            )
        )
    )

    fun getUser(id: String): User? {
        return users.find { it.id == id }
    }

    fun findByEmail(email: String): User? {
        return users.find { it.email.equals(email, ignoreCase = true) }
    }

    fun registerUser(name: String, email: String, password: String, career: String): User? {
        if (findByEmail(email) != null) {
            return null // User already exists
        }
        val newUser = User(
            id = (users.size + 1).toString(),
            name = name,
            email = email,
            passwordHash = password, // Again, hash this in a real app
            career = career,
            skills = emptyList()
        )
        users.add(newUser)
        return newUser
    }
}
