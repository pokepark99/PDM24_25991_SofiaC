package com.example.noticias.domain.use_case

import com.example.noticias.domain.model.NoticiaDetail
import com.example.noticias.domain.repository.NoticiaRepository

class GetNoticiaDetailUseCase(private val repository: NoticiaRepository) {
    suspend operator fun invoke(noticiaUrl:String): NoticiaDetail {
        return repository.getNoticiaDetail(noticiaUrl)
    }
}