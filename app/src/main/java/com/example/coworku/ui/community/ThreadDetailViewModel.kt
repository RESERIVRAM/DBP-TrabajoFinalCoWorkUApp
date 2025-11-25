package com.example.coworku.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.CommunityRepositoryFake
import com.example.coworku.domain.model.ForumPost
import com.example.coworku.domain.model.ForumThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ThreadDetailUiState(
    val isLoading: Boolean = false,
    val thread: ForumThread? = null,
    val posts: List<ForumPost> = emptyList(),
    val errorMessage: String? = null
)

class ThreadDetailViewModel(private val threadId: String) : ViewModel() {

    private val _uiState = MutableStateFlow(ThreadDetailUiState())
    val uiState: StateFlow<ThreadDetailUiState> = _uiState

    private val repository = CommunityRepositoryFake()

    init {
        fetchThreadDetails()
    }

    fun fetchThreadDetails() {
        viewModelScope.launch {
            _uiState.value = ThreadDetailUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                val thread = repository.getForumThreads().find { it.id == threadId }
                val posts = repository.getForumPosts(threadId)
                _uiState.value = ThreadDetailUiState(
                    thread = thread,
                    posts = posts
                )
            } catch (e: Exception) {
                _uiState.value = ThreadDetailUiState(errorMessage = "Error al cargar el tema")
            }
        }
    }

    fun addPost(content: String) {
        viewModelScope.launch {
            val newPost = ForumPost(
                id = "post_new_${System.currentTimeMillis()}",
                threadId = threadId,
                authorName = "Tú",
                authorRole = "Estudiante de Ing. de Software",
                content = content,
                createdAt = "ahora mismo",
                isFromThreadOwner = false
            )
            val currentPosts = _uiState.value.posts
            _uiState.value = _uiState.value.copy(posts = currentPosts + newPost)
        }
    }
}
