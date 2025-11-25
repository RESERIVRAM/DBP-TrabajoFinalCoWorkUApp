package com.example.coworku.ui.community

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    newsId: String,
    onBackClick: () -> Unit,
    viewModel: CommunityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val newsItem = uiState.news.find { it.id == newsId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Noticia") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) {
        paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.padding(16.dp)) {
            if (newsItem != null) {
                item {
                    Text(
                        text = newsItem.tag.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(newsItem.publishedAt, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(newsItem.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(newsItem.fullContent, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
