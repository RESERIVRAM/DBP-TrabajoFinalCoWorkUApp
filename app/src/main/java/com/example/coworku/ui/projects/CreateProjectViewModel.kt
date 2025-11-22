package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val projectRepository = ProjectRepositoryFake()

    fun createProject(name: String, description: String, area: String, skills: List<String>) {
        viewModelScope.launch {
            _uiState.value = CreateProjectUiState(isLoading = true)
            delay(1500) // Simulate network request
            // In a real app, you would make the API call here.
            val newProject = Project(
                id = "p${projectRepository.getProjects().size + 1}",
                name = name,
                description = description,
                area = area,
                status = "Buscando miembros",
                createdDate = Date(),
                requiredSkills = skills.map { Skill(it, "") },
                members = emptyList(),
                rolesAvailable = listOf("Desarrollador", "Diseñador")
            )
            // projectRepository.addProject(newProject) // Fake adding project
            _uiState.value = CreateProjectUiState(isSuccess = true)
        }
    }
}