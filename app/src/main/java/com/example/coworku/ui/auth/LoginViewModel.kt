package com.example.coworku.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.NetworkUserRepository
import com.example.coworku.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val loggedInUser: User? = null,
    val errorMessage: String? = null
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val userRepository = NetworkUserRepository()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            val user = userRepository.login(email, password)

            if (user != null) {
                _uiState.value = LoginUiState(loggedInUser = user)
            } else {
                _uiState.value = LoginUiState(errorMessage = "Credenciales incorrectas o error de red.")
            }
        }
    }
}
