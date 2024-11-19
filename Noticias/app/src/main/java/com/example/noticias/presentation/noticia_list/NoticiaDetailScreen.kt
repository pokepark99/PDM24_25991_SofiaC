package com.example.noticias.presentation.noticia_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noticias.domain.model.Noticia

@Composable
fun NoticiaDetailScreen(
    noticiaDetail: Noticia,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "News Title: ${noticiaDetail.title}")
        Text(text = "News Section: ${noticiaDetail.section}")
        Text(text = "News Subsection: ${noticiaDetail.subsection}")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onBack() }) {
            Text("Back to List")
        }
    }
}
