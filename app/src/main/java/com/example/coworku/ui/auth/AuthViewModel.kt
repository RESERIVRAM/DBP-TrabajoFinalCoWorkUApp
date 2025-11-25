package com.example.coworku.ui.auth

import androidx.lifecycle.ViewModel
import com.example.coworku.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun login(user: User) {
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }
}
