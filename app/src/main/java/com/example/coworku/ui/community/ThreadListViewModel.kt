package com.example.coworku.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.CommunityRepositoryFake
import com.example.coworku.domain.model.ForumThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ThreadListUiState(
    val isLoading: Boolean = false,
    val threads: List<ForumThread> = emptyList(),
    val categoryName: String = "",
    val errorMessage: String? = null
)

class ThreadListViewModel(private val categoryId: String) : ViewModel() {

    private val _uiState = MutableStateFlow(ThreadListUiState())
    val uiState: StateFlow<ThreadListUiState> = _uiState

    private val repository = CommunityRepositoryFake()

    init {
        fetchThreads()
    }

    fun fetchThreads() {
        viewModelScope.launch {
            _uiState.value = ThreadListUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                val allThreads = repository.getForumThreads()
                val categoryThreads = allThreads.filter { it.categoryId == categoryId }
                val category = repository.getForumCategories().find { it.id == categoryId }
                _uiState.value = ThreadListUiState(
                    threads = categoryThreads,
                    categoryName = category?.name ?: "Foro"
                )
            } catch (e: Exception) {
                _uiState.value = ThreadListUiState(errorMessage = "Error al cargar los temas")
            }
        }
    }
}
