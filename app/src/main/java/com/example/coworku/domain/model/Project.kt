package com.example.coworku.domain.model

import java.util.Date

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val area: String,
    val status: String,
    val createdDate: String,
    val requiredSkills: List<Skill>,
    val members: List<ProjectMember>,
    val rolesAvailable: List<String>
)