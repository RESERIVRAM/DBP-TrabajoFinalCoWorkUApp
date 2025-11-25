package com.example.coworku.ui.community

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coworku.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadListScreen(
    categoryId: String,
    onBackClick: () -> Unit,
    onThreadClick: (String) -> Unit,
    onCreateThreadClick: () -> Unit,
) {
    val viewModel: ThreadListViewModel = viewModel(factory = ThreadListViewModelFactory(categoryId))
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.categoryName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(painterResource(id = R.drawable.ic_search), contentDescription = "Buscar en la categoría")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) {
        paddingValues ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(uiState.errorMessage!!)
            }
        } else if (uiState.threads.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                 Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Aún no hay temas en esta categoría. Sé el primero en crear uno.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onCreateThreadClick) {
                        Text("Crear tema")
                    }
                }
            }
        } else {
            LazyColumn(contentPadding = paddingValues) {
                items(uiState.threads) { thread ->
                    ForumThreadRow(thread, onClick = { onThreadClick(thread.id) })
                    HorizontalDivider()
                }
            }
        }
    }
}
