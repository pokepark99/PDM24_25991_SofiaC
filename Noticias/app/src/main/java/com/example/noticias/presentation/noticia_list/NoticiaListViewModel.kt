package com.example.noticias.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.data.remote.RetrofitInstance
import com.example.noticias.data.repository.NoticiasRepositoryImpl
import com.example.noticias.domain.model.Noticia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticiaListViewModel : ViewModel() {
    private val repository = NoticiasRepositoryImpl(RetrofitInstance.api)
    val noticias = MutableStateFlow<List<Noticia>>(emptyList())

    fun fetchNoticias() {
        viewModelScope.launch {
            try {
                val response = repository.getNoticias()
                noticias.value = response
            } catch (e: Exception) {
                println("Failed to fetch noticias: ${e.message}")
            }
        }
    }
}
