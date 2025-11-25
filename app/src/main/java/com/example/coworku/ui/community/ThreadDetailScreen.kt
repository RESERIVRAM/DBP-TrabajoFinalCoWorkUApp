package com.example.coworku.ui.community

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coworku.domain.model.ForumPost
import com.example.coworku.domain.model.ForumThread

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadDetailScreen(
    threadId: String,
    onBackClick: () -> Unit
) {
    val viewModel: ThreadDetailViewModel = viewModel(factory = ThreadDetailViewModelFactory(threadId))
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.thread?.title ?: "", maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            ReplyBar { content ->
                viewModel.addPost(content)
            }
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
        } else {
            LazyColumn(contentPadding = paddingValues) {
                uiState.thread?.let {
                    item { ThreadHeader(it) }
                }
                items(uiState.posts) { post ->
                    ForumPostItem(post)
                    HorizontalDivider() // Corrected
                }
            }
        }
    }
}

@Composable
fun ThreadHeader(thread: ForumThread) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = thread.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(thread.authorName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(4.dp))
            Text("·", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.width(4.dp))
            Text(thread.createdAt, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            thread.tags.forEach {
                Chip(label = it)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun ForumPostItem(post: ForumPost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Placeholder for an avatar
        Box(modifier = Modifier.size(40.dp).clip(CircleShape).align(Alignment.Top)) {
            // You can use a library like Coil to load user images
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(post.authorName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                if (post.isFromThreadOwner) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Creador del tema", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
            }
            Text(post.createdAt, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(post.content, style = MaterialTheme.typography.bodyMedium)

        }
    }
}

@Composable
fun ReplyBar(onSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Surface(tonalElevation = 3.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Escribe una respuesta...") },
                modifier = Modifier.weight(1f),
                shape = CircleShape
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { onSend(text); text = ""; }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar respuesta", tint = MaterialTheme.colorScheme.primary) // Corrected
            }
        }
    }
}


@Composable
private fun Chip(label: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = CircleShape
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
