package com.example.noticias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noticias.presentation.noticia_list.NoticiaDetailScreen
import com.example.noticias.presentation.noticia_list.NoticiaDetailViewModel
import com.example.noticias.presentation.noticia_list.NoticiaListScreen
import com.example.noticias.presentation.noticia_list.NoticiaListViewModel
import com.example.noticias.ui.theme.NoticiasTheme

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
    var selectedNoticiaUrl by remember { mutableStateOf<String?>(null) }

    if(selectedNoticiaUrl == null){
        val noticiaListViewModel: NoticiaListViewModel = viewModel()
        NoticiaListScreen(noticiaListViewModel) { noticiaUrl->
            selectedNoticiaUrl = noticiaUrl
        }
    } else {
        val coinDetailViewModel: NoticiaDetailViewModel = viewModel()
        NoticiaDetailScreen(coinDetailViewModel, selectedNoticiaUrl!!){
            selectedNoticiaUrl = null
        }
    }
}