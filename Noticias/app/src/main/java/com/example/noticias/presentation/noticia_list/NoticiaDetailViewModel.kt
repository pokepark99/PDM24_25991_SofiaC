package com.example.noticias.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.data.remote.RetrofitInstance
import com.example.noticias.data.repository.NoticiasRepositoryImpl
import com.example.noticias.domain.model.Noticia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoticiaDetailViewModel : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = NoticiasRepositoryImpl(api)

    private val _noticiaDetail = MutableStateFlow<Noticia?>(null)
    val noticiaDetail = _noticiaDetail.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun fetchNoticiaDetail(noticiaUrl: String) {
        viewModelScope.launch {
            try {
                val articles = repository.getNoticias()

                val selectedNoticia = articles.find { it.url == noticiaUrl }

                if (selectedNoticia != null) {
                    _noticiaDetail.value = selectedNoticia
                } else {
                    _error.value = "Article not found"
                }
            } catch (e: Exception) {
                _error.value = "Failed to fetch article details: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}