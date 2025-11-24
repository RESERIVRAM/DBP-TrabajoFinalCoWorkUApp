package com.example.coworku.data.remote

import com.example.coworku.domain.model.Project
import retrofit2.http.GET

interface CoWorkUApi {
    @GET("api/ProjectsApi") // Debe coincidir con la ruta de tu navegador
    suspend fun getProjects(): List<Project>
}