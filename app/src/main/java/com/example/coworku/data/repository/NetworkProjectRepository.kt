package com.example.coworku.data.repository

import com.example.coworku.data.remote.RetrofitClient
import com.example.coworku.domain.model.Project

class NetworkProjectRepository {
    // Usamos la instancia 'api' que ya está configurada en RetrofitClient
    private val api = RetrofitClient.api

    suspend fun getProjects(): List<Project> {
        return api.getProjects()
    }

    suspend fun getProject(id: Int): Project? {
        return try {
            // Ahora que el servidor tiene el método GetProject(id), esto funcionará
            api.getProjectById(id)
        } catch (e: Exception) {
            e.printStackTrace() // Imprime el error en el Logcat si falla
            null
        }
    }
}