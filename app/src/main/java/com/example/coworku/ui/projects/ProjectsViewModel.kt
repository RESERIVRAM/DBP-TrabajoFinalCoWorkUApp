package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.ProjectRepositoryFake
import com.example.coworku.domain.model.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val errorMessage: String? = null
)

class ProjectsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState

    private val projectRepository = ProjectRepositoryFake()

    init {
        fetchProjects()
    }

    fun fetchProjects() {
        viewModelScope.launch {
            _uiState.value = ProjectsUiState(isLoading = true)
            delay(1500) // Simulate network delay
            try {
                val projects = projectRepository.getProjects()
                _uiState.value = ProjectsUiState(projects = projects)
            } catch (e: Exception) {
                _uiState.value = ProjectsUiState(errorMessage = "Error al cargar los proyectos")
            }
        }
    }
}