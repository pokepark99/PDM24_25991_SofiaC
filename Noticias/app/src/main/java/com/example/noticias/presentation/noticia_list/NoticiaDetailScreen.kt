package com.example.noticias.presentation.noticia_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.noticias.domain.model.Noticia

@Composable
fun NoticiaDetailScreen(
    noticiaDetail: Noticia,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                text = "Title: ${noticiaDetail.title}",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Byline: ${noticiaDetail.byline}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Abstract: ${noticiaDetail.abstract}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        noticiaDetail.multimedia?.let { multimediaList ->
            val groupedByCaption = multimediaList.groupBy { it.caption }

            if (groupedByCaption.isNotEmpty()) {
                item {
                    Text(
                        text = "Multimedia:",
                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                groupedByCaption.forEach { (caption, mediaList) ->
                    item {
                        Text(
                            text = "Caption: $caption",
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(mediaList) { media ->
                        Text(
                            text = "URL: ${media.url}",
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            color = Color.Blue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            } else {
                item {
                    Text(
                        text = "No multimedia available.",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onBack() }) {
                Text("Back to List")
            }
        }
    }
}