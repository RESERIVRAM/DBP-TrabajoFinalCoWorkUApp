package com.example.coworku.data.repository

import android.util.Log
import com.example.coworku.data.remote.RetrofitClient
import com.example.coworku.domain.model.Project

class NetworkProjectRepository {
    private val api = RetrofitClient.api

    suspend fun getProjects(): List<Project> {
        return try {
            val response = api.getProjects()
            Log.d("API_SUCCESS", "Se cargaron ${response.size} proyectos")
            response
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error cargando proyectos: ${e.message}")
            // Si falla, retornamos lista vacía o lanzamos el error para que el ViewModel lo maneje
            emptyList()
        }
    }

    // Por ahora, si necesitas getProject(id), puedes filtrar de la lista o crear otro endpoint
    suspend fun getProject(id: Int): Project? {
        // Opción temporal: Traer todos y buscar localmente (ineficiente pero funciona para demo)
        return getProjects().find { it.id == id }
    }
}