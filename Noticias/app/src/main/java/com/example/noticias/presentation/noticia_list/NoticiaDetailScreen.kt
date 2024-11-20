package com.example.noticias.presentation.noticia_list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.noticias.domain.model.Noticia

@Composable
fun NoticiaDetailScreen(
    noticiaDetail: Noticia, // Detalhes da notícia
    onBack: () -> Unit // Ação para voltar à lista
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { //Print de campos no ecra
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

        item {
            Text(
                text = "Metadata",
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Section: ${noticiaDetail.section}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Subsection: ${noticiaDetail.subsection}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Material Type: ${noticiaDetail.material_type_facet}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Created Date: ${noticiaDetail.created_date}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Updated Date: ${noticiaDetail.updated_date}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(
                text = "Additional Info",
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (!noticiaDetail.short_url.isNullOrBlank()) {
                Text(
                    text = "Short URL: ${noticiaDetail.short_url}",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = "URI: ${noticiaDetail.uri}",
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onBack() }) {
                Text("Back to List")
            }
        }
    }
}
