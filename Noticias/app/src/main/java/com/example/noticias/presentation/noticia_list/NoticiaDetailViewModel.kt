package com.example.noticias.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.data.remote.RetrofitInstance
import com.example.noticias.data.repository.NoticiasRepositoryImpl
import com.example.noticias.domain.model.NoticiaDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticiaDetailViewModel : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = NoticiasRepositoryImpl(api)

    val noticiaDetail = MutableStateFlow<NoticiaDetail?>(null)

    fun fetchNoticiaDetail(noticiaUrl: String) {
        viewModelScope.launch {
            try {
                noticiaDetail.value = repository.getNoticiaDetail(noticiaUrl) // Call repository directly
            } catch (e: Exception) {
                noticiaDetail.value = null
            }
        }
    }
}