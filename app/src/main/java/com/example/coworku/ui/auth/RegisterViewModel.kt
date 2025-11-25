package com.example.coworku.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.NetworkUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false,
    val errorMessage: String? = null
)

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val userRepository = NetworkUserRepository()

    fun registerUser(name: String, lastName: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (password != confirmPassword) {
                _uiState.value = RegisterUiState(errorMessage = "Las contraseñas no coinciden")
                return@launch
            }

            _uiState.value = RegisterUiState(isLoading = true)

            val success = userRepository.register(name, lastName, email, password)

            if (success) {
                _uiState.value = RegisterUiState(registrationSuccess = true)
            } else {
                _uiState.value = RegisterUiState(errorMessage = "El correo electrónico ya está en uso o hubo un error de red.")
            }
        }
    }
}
