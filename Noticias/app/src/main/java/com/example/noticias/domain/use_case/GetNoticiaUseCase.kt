package com.example.noticias.domain.use_case

import com.example.noticias.domain.model.Noticia
import com.example.noticias.domain.repository.NoticiaRepository

class GetNoticiaUseCase(private val repository: NoticiaRepository){
    suspend operator fun invoke(): List<Noticia> {
        return repository.getNoticias()
    }
}