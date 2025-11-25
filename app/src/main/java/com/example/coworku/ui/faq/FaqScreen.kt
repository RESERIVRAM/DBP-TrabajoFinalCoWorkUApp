package com.example.coworku.ui.faq

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coworku.domain.model.FaqItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    onBackClick: () -> Unit,
    viewModel: FaqViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var expandedItem by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centro de ayuda") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) {
        paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.padding(horizontal = 16.dp)) {
            item {
                Text(
                    "Encuentra respuestas rápidas sobre CoWorkU, proyectos y la comunidad.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            if(uiState.isLoading){
                item{
                    Box(modifier = Modifier.fillParentMaxSize()){
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            uiState.faqItemsByCategory.forEach { (category, items) ->
                item {
                    Text(
                        category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                items(items) { faqItem ->
                    FaqItem(
                        item = faqItem,
                        expanded = expandedItem == faqItem.id,
                        onClick = { 
                            expandedItem = if(expandedItem == faqItem.id) null else faqItem.id
                         }
                    )
                    HorizontalDivider()
                }
            }

            item{
                Spacer(Modifier.height(32.dp))
                ContactSupportCard()
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun FaqItem(item: FaqItem, expanded: Boolean, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
            Text(item.question, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Icon(
                if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if(expanded) "Colapsar" else "Expandir"
            )
        }
        AnimatedVisibility(visible = expanded) {
            Text(
                item.answer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun ContactSupportCard(){
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("¿No encontraste lo que buscabas?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))
            Row{
                Button(onClick = { /*TODO*/ }) {
                    Text("Escribir al soporte")
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = { /*TODO*/ }) {
                     Text("Unirte a la comunidad")
                }
            }
        }
    }
}
