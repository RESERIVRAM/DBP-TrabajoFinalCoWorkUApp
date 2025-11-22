package com.example.coworku.domain.model

data class JoinRequest(
    val id: String,
    val projectId: String,
    val userId: String,
    val message: String,
    val status: String // "pending", "approved", "rejected"
)