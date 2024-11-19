package com.example.noticias.presentation.noticia_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.noticias.domain.model.Noticia

@Composable
fun NoticiaListScreen(
    viewModel: NoticiaListViewModel,
    onNoticiaSelected: (Noticia) -> Unit
) {
    val noticias = viewModel.noticias.collectAsState().value

    if (noticias.isEmpty()) {
        viewModel.fetchNoticias()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(noticias) { noticia ->
            NoticiaItem(noticia = noticia, onNoticiaSelected = onNoticiaSelected)
        }
    }
}

@Composable
fun NoticiaItem(
    noticia: Noticia,
    onNoticiaSelected: (Noticia) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onNoticiaSelected(noticia) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = noticia.title,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Published Date
            noticia.published_date?.let { date ->
                Text(
                    text = "Published: $date",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = noticia.url,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}