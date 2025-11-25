package com.example.coworku.domain.model

data class ForumThread(
    val id: String,
    val categoryId: String,
    val title: String,
    val authorName: String,
    val authorRole: String,
    val excerpt: String,
    val createdAt: String,
    val repliesCount: Int,
    val isPinned: Boolean,
    val tags: List<String>,
    val lastActivity: String
)
