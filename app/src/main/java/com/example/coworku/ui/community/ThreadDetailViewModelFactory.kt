package com.example.coworku.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ThreadDetailViewModelFactory(private val threadId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThreadDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThreadDetailViewModel(threadId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
