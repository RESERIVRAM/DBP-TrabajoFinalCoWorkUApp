package com.example.coworku.data.repository.fake

import com.example.coworku.domain.model.Project
import com.example.coworku.domain.model.ProjectMember
import com.example.coworku.domain.model.Skill
import com.example.coworku.domain.model.User
import java.util.Date

class ProjectRepositoryFake {
    private val projects = mutableListOf(
        Project(
            id = 1,
            name = "CoWorkU App",
            description = "Desarrollo de la app móvil para la plataforma CoWorkU.",
            area = "Desarrollo Móvil",
            status = "Buscando miembros",
            createdDate = "2025-11-24T10:00:00",
            requiredSkills = listOf(Skill("Kotlin", "Avanzado"), Skill("Jetpack Compose", "Intermedio")),
            members = listOf(
                ProjectMember(User("1", "Renato Riva", "renato.riva@example.com", "Ingeniería de Software", emptyList()), "Líder")
            ),
            rolesAvailable = listOf("Desarrollador", "Diseñador")
        )
    )

    fun getProjects(): List<Project> {
        return projects
    }

    fun getProject(id: Int): Project? {
        return projects.find { it.id == id }
    }
}