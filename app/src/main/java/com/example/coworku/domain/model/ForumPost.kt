package com.example.coworku.domain.model

data class ForumPost(
    val id: String,
    val threadId: String,
    val authorName: String,
    val authorRole: String,
    val content: String,
    val createdAt: String,
    val isFromThreadOwner: Boolean = false
)
