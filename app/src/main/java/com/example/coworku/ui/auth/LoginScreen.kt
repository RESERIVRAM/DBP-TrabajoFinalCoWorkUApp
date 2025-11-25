package com.example.coworku.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginViewModel: LoginViewModel = viewModel()
    val uiState by loginViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it }, // Corrected this line
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null
        )

        uiState.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { loginViewModel.loginUser(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }
        }

        TextButton(onClick = { navController.navigate("register") }) {
            Text("¿No tienes cuenta? Regístrate")
        }

        // This effect will run when the login is successful
        LaunchedEffect(uiState.loggedInUser) {
            uiState.loggedInUser?.let { user ->
                authViewModel.login(user)
                navController.navigate("home") {
                    // Clear back stack
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        }
    }
}
