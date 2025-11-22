package com.example.coworku.ui.projects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coworku.ui.components.CoWorkUPrimaryButton
import com.example.coworku.ui.components.TagChip

@Composable
fun ProjectDetailScreen(navController: NavController, viewModel: ProjectDetailViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage != null -> Text(uiState.errorMessage!!)
            uiState.project == null -> Text("Proyecto no encontrado")
            else -> {
                val project = uiState.project!!
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(project.name, style = MaterialTheme.typography.headlineLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(project.status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(project.area, style = MaterialTheme.typography.titleMedium)
                    }
                    item {
                        Text("Descripción", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(project.description, style = MaterialTheme.typography.bodyMedium)
                    }
                    item {
                        Text("Habilidades Requeridas", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            project.requiredSkills.forEach { skill ->
                                TagChip(text = skill.name)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                    item {
                        Text("Equipo Actual", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        project.members.forEach {
                            Text("${it.user.name} - ${it.role}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    item {
                        Text("Roles Disponibles", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        project.rolesAvailable.forEach {
                            Text(it, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    item {
                        if (project.status != "Finalizado") {
                            CoWorkUPrimaryButton(text = "Unirse al Proyecto") {
                                navController.navigate("joinProject")
                            }
                        }
                    }
                }
            }
        }
    }
}