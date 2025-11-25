package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.NetworkProjectRepository
import com.example.coworku.domain.model.Project
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

    private val projectRepository = NetworkProjectRepository()

    init {
        fetchProjects()
    }

    fun fetchProjects() {
        _uiState.value = ProjectsUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val projects = projectRepository.getProjects()
                _uiState.value = ProjectsUiState(projects = projects)
            } catch (e: Exception) {
                // Consider more specific error handling
                _uiState.value = ProjectsUiState(errorMessage = "Error de red: ${e.message}")
            }
        }
    }
}
