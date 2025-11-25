package com.example.coworku.domain.model

import androidx.annotation.DrawableRes

data class ForumCategory(
    val id: String,
    val name: String,
    val description: String,
    @DrawableRes val icon: Int? = null
)
