package com.example.coworku.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage != null -> Text(uiState.errorMessage!!)
            uiState.user == null -> Text("No se pudo cargar el perfil")
            else -> {
                val user = uiState.user!!
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = user.name.first().toString(), style = MaterialTheme.typography.headlineLarge)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(user.name, style = MaterialTheme.typography.headlineMedium)
                        Text(user.career, style = MaterialTheme.typography.bodyLarge)
                        Text(user.email, style = MaterialTheme.typography.bodyMedium)
                    }
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text("Habilidades", style = MaterialTheme.typography.titleLarge)
                    }
                    items(user.skills) { skill ->
                        Text("${skill.name} - ${skill.level}")
                    }
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(onClick = { /* TODO: Implement add skill */ }) {
                            Text("Añadir Habilidad")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            navController.navigate("landing") {
                                popUpTo("home") { inclusive = true }
                            }
                        }) {
                            Text("Cerrar Sesión")
                        }
                    }
                }
            }
        }
    }
}