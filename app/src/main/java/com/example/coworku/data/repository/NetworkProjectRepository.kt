package com.example.coworku.data.repository

import com.example.coworku.data.remote.CoWorkUApi
import com.example.coworku.data.remote.RetrofitClient
import com.example.coworku.domain.model.Project

class NetworkProjectRepository {
    private val api: CoWorkUApi by lazy {
        RetrofitClient.retrofit.create(CoWorkUApi::class.java)
    }

    suspend fun getProjects(): List<Project> {
        return api.getProjects()
    }

    suspend fun getProject(id: Int): Project? {
        return try {
            api.getProjectById(id)
        } catch (e: Exception) {
            null
        }
    }
}
