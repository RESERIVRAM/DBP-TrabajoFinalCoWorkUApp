package com.example.coworku.ui.community

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateThreadScreen(
    onBackClick: () -> Unit,
    onThreadCreated: (String) -> Unit
) {
    val viewModel: CommunityViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear nuevo tema") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        // Icon is handled by the caller
                    }
                }
            )
        }
    ) {
        paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = uiState.categories.find{ it.id == selectedCategory}?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    uiState.categories.forEach{
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = { selectedCategory = it.id; expanded = false })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Contenido") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = { /* TODO */ onThreadCreated("new_thread_id") }) {
                    Text("Publicar tema")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onBackClick) {
                    Text("Cancelar")
                }
            }
        }
    }
}
