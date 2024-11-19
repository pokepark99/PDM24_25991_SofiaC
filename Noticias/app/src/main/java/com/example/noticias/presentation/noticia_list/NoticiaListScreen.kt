package com.example.noticias.presentation.noticia_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
            .clickable { onNoticiaSelected(noticia) }
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = noticia.title)
            noticia.publishedDate?.let { Text(text = it) }
            Text(text = noticia.url)
        }
    }
}