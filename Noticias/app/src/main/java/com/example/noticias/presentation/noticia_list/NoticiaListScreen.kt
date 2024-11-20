package com.example.noticias.presentation.noticia_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    // Obtém a lista de notícias ordenadas
    val sortedNoticias = viewModel.sortedNoticias.collectAsState(initial = emptyList()).value

    Column {
        //opcoes de ordenar
        SortOptions(viewModel)

        //scroll vertical
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            //apresenta as noticas
            items(sortedNoticias) { noticia ->
                NoticiaItem(noticia = noticia, onNoticiaSelected = onNoticiaSelected)
            }
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
            .padding(vertical = 4.dp)
            .clickable { onNoticiaSelected(noticia) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = noticia.title,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            //so amostra se existir (nao vazio)
            if (!noticia.kicker.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = noticia.kicker ?: "",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Published: ${noticia.published_date}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = noticia.url,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                color = Color.Blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Clip
            )
        }
    }
}

@Composable
fun SortOptions(viewModel: NoticiaListViewModel) {
    val options = listOf("Default", "A-Z", "Date")
    val sortCriteria = viewModel.sortCriteriaFlow.collectAsState().value

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = when (index) {
                1 -> sortCriteria == NoticiaListViewModel.SortCriteria.ALPHABETICAL
                2 -> sortCriteria == NoticiaListViewModel.SortCriteria.PUBLISHED_DATE
                else -> sortCriteria == NoticiaListViewModel.SortCriteria.NONE
            }
            Button(
                onClick = {
                    viewModel.setSortCriteria(
                        when (index) {
                            1 -> NoticiaListViewModel.SortCriteria.ALPHABETICAL
                            2 -> NoticiaListViewModel.SortCriteria.PUBLISHED_DATE
                            else -> NoticiaListViewModel.SortCriteria.NONE
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.Gray else Color.LightGray
                )
            ) {
                Text(text = option)
            }
        }
    }
}