package com.example.coworku.domain.model

data class NewsItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val contentPreview: String,
    val fullContent: String,
    val publishedAt: String,
    val tag: String
)
