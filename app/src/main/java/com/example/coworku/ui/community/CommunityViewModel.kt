package com.example.coworku.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.CommunityRepositoryFake
import com.example.coworku.domain.model.ForumCategory
import com.example.coworku.domain.model.ForumThread
import com.example.coworku.domain.model.NewsItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CommunityUiState(
    val isLoading: Boolean = false,
    val categories: List<ForumCategory> = emptyList(),
    val pinnedThreads: List<ForumThread> = emptyList(),
    val news: List<NewsItem> = emptyList(),
    val errorMessage: String? = null
)

class CommunityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState: StateFlow<CommunityUiState> = _uiState

    private val repository = CommunityRepositoryFake()

    init {
        fetchCommunityData()
    }

    fun fetchCommunityData() {
        viewModelScope.launch {
            _uiState.value = CommunityUiState(isLoading = true)
            delay(1500) // Simulate network delay
            try {
                val categories = repository.getForumCategories()
                val allThreads = repository.getForumThreads()
                val pinnedThreads = allThreads.filter { it.isPinned }
                val news = repository.getNewsItems()
                _uiState.value = CommunityUiState(
                    categories = categories,
                    pinnedThreads = pinnedThreads,
                    news = news
                )
            } catch (e: Exception) {
                _uiState.value = CommunityUiState(errorMessage = "Error al cargar la comunidad")
            }
        }
    }
}
