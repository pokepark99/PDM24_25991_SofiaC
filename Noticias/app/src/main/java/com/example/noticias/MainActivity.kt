package com.example.noticias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noticias.domain.model.Noticia
import com.example.noticias.presentation.noticia_list.NoticiaDetailScreen
import com.example.noticias.presentation.noticia_list.NoticiaListScreen
import com.example.noticias.presentation.noticia_list.NoticiaListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val noticiaListViewModel: NoticiaListViewModel = viewModel()
    val noticias by noticiaListViewModel.noticias.collectAsState()

    if (noticias.isEmpty()) {
        noticiaListViewModel.fetchNoticias()
    }

    var selectedNoticia by remember { mutableStateOf<Noticia?>(null) }

    //navegacao de ecras
    if (selectedNoticia == null) {
        NoticiaListScreen(noticiaListViewModel) { noticia ->
            selectedNoticia = noticia
        }
    } else {
        NoticiaDetailScreen(
            noticiaDetail = selectedNoticia!!,
            onBack = { selectedNoticia = null }
        )
    }
}
