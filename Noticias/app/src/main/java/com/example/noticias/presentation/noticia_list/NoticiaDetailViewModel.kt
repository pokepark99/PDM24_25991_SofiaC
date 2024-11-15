package com.example.noticias.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.data.remote.RetrofitInstance
import com.example.noticias.data.repository.NoticiasRepositoryImpl
import com.example.noticias.domain.model.NoticiaDetail
import com.example.noticias.domain.use_case.GetNoticiaDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoticiaDetailViewModel : ViewModel(){
    private val api = RetrofitInstance.api
    private val repository = NoticiasRepositoryImpl(api)
    private val getNoticiaDetailUseCase = GetNoticiaDetailUseCase(repository)

    val noticiaDetail = MutableStateFlow<NoticiaDetail?>(null)

    fun fetchNoticiaDetail(noticiaUrl: String){
        viewModelScope.launch {
            try {
                noticiaDetail.value = getNoticiaDetailUseCase(noticiaUrl)
            } catch (e: Exception) {
                noticiaDetail.value = null
            }
        }
    }
}