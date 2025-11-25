package com.example.coworku.ui.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coworku.R
import com.example.coworku.domain.model.ForumCategory
import com.example.coworku.domain.model.ForumThread
import com.example.coworku.domain.model.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityHomeScreen(
    onCategoryClick: (String) -> Unit,
    onThreadClick: (String) -> Unit,
    onNewsClick: (String) -> Unit,
    onCreateThreadClick: () -> Unit,
    onGoToHelpCenter: () -> Unit,
    viewModel: CommunityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comunidad") },
                actions = {
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(painterResource(id = R.drawable.ic_search), contentDescription = "Buscar en la comunidad")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateThreadClick) {
                Icon(Icons.Default.Add, contentDescription = "Crear tema")
            }
        }
    ) { paddingValues ->
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
                item {
                    CommunityHeader(onGoToHelpCenter)
                }
                item {
                    ForumCategoriesSection(uiState.categories, onCategoryClick)
                }
                item {
                    PinnedThreadsSection(uiState.pinnedThreads, onThreadClick)
                }
                item {
                    NewsSection(uiState.news, onNewsClick)
                }
            }
        }
    }
}

@Composable
fun CommunityHeader(onGoToHelpCenter: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Conecta con otros estudiantes, comparte tus dudas y descubre novedades de CoWorkU.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onGoToHelpCenter) {
            Text("Centro de ayuda")
        }
    }
}

@Composable
fun ForumCategoriesSection(categories: List<ForumCategory>, onCategoryClick: (String) -> Unit) {
    Column {
        Text(
            text = "Categorías del foro",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                ForumCategoryCard(category, onClick = { onCategoryClick(category.id) })
            }
        }
    }
}

@Composable
fun PinnedThreadsSection(threads: List<ForumThread>, onThreadClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Destacados del foro",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            threads.forEach { thread ->
                ForumThreadRow(thread, onClick = { onThreadClick(thread.id) })
            }
        }
    }
}

@Composable
fun NewsSection(news: List<NewsItem>, onNewsClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = "Novedades de CoWorkU",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            news.forEach { newsItem ->
                NewsItemCard(newsItem, onClick = { onNewsClick(newsItem.id) })
            }
        }
    }
}


@Composable
fun ForumCategoryCard(category: ForumCategory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // If you have icons, you can display them here
            Text(text = category.name, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = category.description, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun ForumThreadRow(thread: ForumThread, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        color = Color.Transparent
    ) {
        Column {
            Text(text = thread.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(
                text = thread.excerpt,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = thread.authorName, style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${thread.repliesCount} respuestas", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = thread.lastActivity, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = newsItem.tag.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsItem.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = newsItem.subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = newsItem.publishedAt, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
