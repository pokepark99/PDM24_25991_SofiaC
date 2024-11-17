package com.example.noticias.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.data.remote.RetrofitInstance
import com.example.noticias.data.repository.NoticiasRepositoryImpl
import com.example.noticias.domain.model.Noticia
import com.example.noticias.domain.use_case.GetNoticiaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticiaListViewModel : ViewModel(){
    private val api = RetrofitInstance.api
    private val repository = NoticiasRepositoryImpl(api)
    private val getNoticiaUseCase = GetNoticiaUseCase(repository)

    val noticias = MutableStateFlow<List<Noticia>>(emptyList())

    fun fetchNoticias(){
        viewModelScope.launch {
            try {
                val result = getNoticiaUseCase()
                println("Fetched noticias: $result")
            } catch (e: Exception) {
                println("Failed to fetch noticias: ${e.message}")
                noticias.value = emptyList()
            }
        }
    }
}