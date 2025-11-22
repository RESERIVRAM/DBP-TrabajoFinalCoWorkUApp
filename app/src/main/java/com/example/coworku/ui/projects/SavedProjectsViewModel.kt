package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.ProjectRepositoryFake
import com.example.coworku.domain.model.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SavedProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val errorMessage: String? = null
)

class SavedProjectsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SavedProjectsUiState())
    val uiState: StateFlow<SavedProjectsUiState> = _uiState

    private val projectRepository = ProjectRepositoryFake()

    init {
        fetchSavedProjects()
    }

    fun fetchSavedProjects() {
        viewModelScope.launch {
            _uiState.value = SavedProjectsUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                // In a real app, you would get only the saved projects for the current user
                // For now, we return a subset of all projects for demonstration
                val projects = projectRepository.getProjects().take(1)
                _uiState.value = SavedProjectsUiState(projects = projects)
            } catch (e: Exception) {
                _uiState.value = SavedProjectsUiState(errorMessage = "Error al cargar los proyectos guardados")
            }
        }
    }
}