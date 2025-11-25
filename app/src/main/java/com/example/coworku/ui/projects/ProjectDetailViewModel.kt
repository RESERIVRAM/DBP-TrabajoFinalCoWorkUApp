package com.example.coworku.ui.projects

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.NetworkProjectRepository
import com.example.coworku.domain.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProjectDetailUiState(
    val isLoading: Boolean = false,
    val project: Project? = null,
    val errorMessage: String? = null
)

class ProjectDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectDetailUiState())
    val uiState: StateFlow<ProjectDetailUiState> = _uiState

    // Safely get projectId from navigation arguments
    private val projectId: Int = savedStateHandle.get<String>("projectId")?.toIntOrNull() ?: 0

    init {
        if (projectId > 0) {
            fetchProjectDetails()
        } else {
            _uiState.value = ProjectDetailUiState(errorMessage = "ID de proyecto inválido")
        }
    }

    fun fetchProjectDetails() {
        viewModelScope.launch {
            _uiState.value = ProjectDetailUiState(isLoading = true)
            try {
                // Use the projectId from the class property
                val project = NetworkProjectRepository().getProject(projectId)
                _uiState.value = ProjectDetailUiState(project = project)
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(errorMessage = "Error al cargar el proyecto: ${e.message}")
            }
        }
    }
}
