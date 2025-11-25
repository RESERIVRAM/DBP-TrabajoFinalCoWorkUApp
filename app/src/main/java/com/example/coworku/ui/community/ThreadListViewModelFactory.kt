package com.example.coworku.ui.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ThreadListViewModelFactory(private val categoryId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThreadListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThreadListViewModel(categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
