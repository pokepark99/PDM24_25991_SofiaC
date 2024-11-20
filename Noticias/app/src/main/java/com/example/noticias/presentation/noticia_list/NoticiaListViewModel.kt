package com.example.noticias.presentation.noticia_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticias.data.remote.RetrofitInstance
import com.example.noticias.data.repository.NoticiasRepositoryImpl
import com.example.noticias.domain.model.Noticia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NoticiaListViewModel : ViewModel() {
    private val repository = NoticiasRepositoryImpl(RetrofitInstance.api)

    val noticias = MutableStateFlow<List<Noticia>>(emptyList())

    private val sortCriteria = MutableStateFlow(SortCriteria.NONE)
    val sortCriteriaFlow: StateFlow<SortCriteria> = sortCriteria

    val sortedNoticias = combine(noticias, sortCriteria) { noticiasList, criteria ->
        when (criteria) {
            SortCriteria.ALPHABETICAL -> noticiasList.sortedBy { it.title }
            SortCriteria.PUBLISHED_DATE -> noticiasList.sortedByDescending { it.published_date }
            else -> noticiasList
        }
    }

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

    fun setSortCriteria(criteria: SortCriteria) {
        sortCriteria.value = criteria
    }

    enum class SortCriteria {
        NONE, ALPHABETICAL, PUBLISHED_DATE
    }
}
