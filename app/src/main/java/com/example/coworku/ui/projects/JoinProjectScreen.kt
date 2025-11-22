package com.example.coworku.ui.projects

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun JoinProjectScreen(navController: NavController, viewModel: JoinProjectViewModel = viewModel()) {
    var message by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate("joinProjectSuccess")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Unirse al Proyecto", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Cuéntanos por qué quieres unirte") },
            modifier = Modifier.fillMaxWidth().height(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // In a real app, you'd get the available roles from the project details
        OutlinedTextField(
            value = selectedRole,
            onValueChange = { selectedRole = it },
            label = { Text("Rol al que postulas") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { viewModel.sendJoinRequest(message, selectedRole) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Enviar Solicitud")
            }
        }
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Cancelar")
        }
    }
}
