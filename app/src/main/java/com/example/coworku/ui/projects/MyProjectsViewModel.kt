package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.ProjectRepositoryFake
import com.example.coworku.domain.model.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MyProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList(),
    val errorMessage: String? = null
)

class MyProjectsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyProjectsUiState())
    val uiState: StateFlow<MyProjectsUiState> = _uiState

    private val projectRepository = ProjectRepositoryFake()

    init {
        fetchMyProjects()
    }

    fun fetchMyProjects() {
        viewModelScope.launch {
            _uiState.value = MyProjectsUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                // In a real app, you would filter projects by the current user's ID
                val projects = projectRepository.getProjects()
                _uiState.value = MyProjectsUiState(projects = projects)
            } catch (e: Exception) {
                _uiState.value = MyProjectsUiState(errorMessage = "Error al cargar tus proyectos")
            }
        }
    }
}