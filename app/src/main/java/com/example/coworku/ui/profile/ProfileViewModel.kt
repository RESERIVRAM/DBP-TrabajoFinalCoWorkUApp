package com.example.coworku.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.UserRepositoryFake
import com.example.coworku.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val userRepository = UserRepositoryFake()

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                // In a real app, you would get the user from a data source
                val user = userRepository.getUser("1")
                _uiState.value = ProfileUiState(user = user)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState(errorMessage = "Error al cargar el perfil")
            }
        }
    }
}