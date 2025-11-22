package com.example.coworku.ui.projects

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.ProjectRepositoryFake
import com.example.coworku.domain.model.Project
import kotlinx.coroutines.delay
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

    private val projectId: String = savedStateHandle.get<String>("projectId")!!
    private val projectRepository = ProjectRepositoryFake()

    init {
        fetchProjectDetails()
    }

    fun fetchProjectDetails() {
        viewModelScope.launch {
            _uiState.value = ProjectDetailUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                val project = projectRepository.getProject(projectId)
                _uiState.value = ProjectDetailUiState(project = project)
            } catch (e: Exception) {
                _uiState.value = ProjectDetailUiState(errorMessage = "Error al cargar el proyecto")
            }
        }
    }
}