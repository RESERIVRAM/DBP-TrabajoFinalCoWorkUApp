package com.example.coworku.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class JoinProjectUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class JoinProjectViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JoinProjectUiState())
    val uiState: StateFlow<JoinProjectUiState> = _uiState

    fun sendJoinRequest(message: String, role: String) {
        viewModelScope.launch {
            _uiState.value = JoinProjectUiState(isLoading = true)
            delay(1500) // Simulate network request
            // In a real app, you would make the API call here.
            _uiState.value = JoinProjectUiState(isSuccess = true)
        }
    }
}