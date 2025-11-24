package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.NetworkProjectRepository
import com.example.coworku.data.repository.fake.ProjectRepositoryFake
import com.example.coworku.domain.model.Project
import com.example.coworku.domain.model.Skill
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

data class CreateProjectUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class CreateProjectViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProjectUiState())
    val uiState: StateFlow<CreateProjectUiState> = _uiState

    private val projectRepository = NetworkProjectRepository()

    fun createProject(name: String, description: String, area: String, skills: List<String>) {
        viewModelScope.launch {
            _uiState.value = CreateProjectUiState(isLoading = true)
            delay(1500) // Simulate network request

            // Aquí en el futuro llamaremos a la API para GUARDAR de verdad.
            // Por ahora, tu código calcula el ID basándose en la cantidad REAL de proyectos en SQL Server.
            val newId = try {
                projectRepository.getProjects().size + 1
            } catch (e: Exception) { 1 }

            val newProject = Project(
                id = newId,
                name = name,
                description = description,
                area = area,
                status = "Buscando miembros",
                createdDate = java.util.Date().toString(),
                requiredSkills = skills.map { Skill(it, "") },
                members = emptyList(),
                rolesAvailable = listOf("Desarrollador", "Diseñador")
            )
            // projectRepository.addProject(newProject) // Fake adding project
            _uiState.value = CreateProjectUiState(isSuccess = true)
        }
    }
}