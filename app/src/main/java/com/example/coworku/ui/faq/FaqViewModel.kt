package com.example.coworku.ui.faq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworku.data.repository.fake.CommunityRepositoryFake
import com.example.coworku.domain.model.FaqItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class FaqUiState(
    val isLoading: Boolean = false,
    val faqItemsByCategory: Map<String, List<FaqItem>> = emptyMap(),
    val errorMessage: String? = null
)

class FaqViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FaqUiState())
    val uiState: StateFlow<FaqUiState> = _uiState

    private val repository = CommunityRepositoryFake()

    init {
        fetchFaqItems()
    }

    fun fetchFaqItems() {
        viewModelScope.launch {
            _uiState.value = FaqUiState(isLoading = true)
            delay(1000) // Simulate network delay
            try {
                val items = repository.getFaqItems()
                val groupedItems = items.groupBy { it.category }
                _uiState.value = FaqUiState(faqItemsByCategory = groupedItems)
            } catch (e: Exception) {
                _uiState.value = FaqUiState(errorMessage = "Error al cargar las preguntas frecuentes")
            }
        }
    }
}
