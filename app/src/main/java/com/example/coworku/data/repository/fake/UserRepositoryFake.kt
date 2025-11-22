package com.example.coworku.data.repository.fake

import com.example.coworku.domain.model.User
import com.example.coworku.domain.model.Skill

class UserRepositoryFake {
    private val users = mutableListOf(
        User(
            id = "1",
            name = "Renato Riva",
            email = "renato.riva@example.com",
            career = "Ingeniería de Software",
            skills = listOf(
                Skill("Kotlin", "Avanzado"),
                Skill("Jetpack Compose", "Intermedio")
            )
        )
    )

    fun getUser(id: String): User? {
        return users.find { it.id == id }
    }
}