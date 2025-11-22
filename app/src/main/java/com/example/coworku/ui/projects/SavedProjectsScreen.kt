package com.example.coworku.ui.projects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coworku.ui.components.ProjectCard

@Composable
fun SavedProjectsScreen(navController: NavController, viewModel: SavedProjectsViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.errorMessage != null -> Text(uiState.errorMessage!!)
                uiState.projects.isEmpty() -> {
                    Text("Aún no has guardado proyectos")
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(uiState.projects) { project ->
                            ProjectCard(project = project) {
                                navController.navigate("projectDetail/${project.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}