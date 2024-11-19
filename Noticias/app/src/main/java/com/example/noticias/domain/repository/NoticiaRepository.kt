package com.example.noticias.domain.repository

import com.example.noticias.domain.model.Noticia

interface NoticiaRepository {
    suspend fun getNoticias(): List<Noticia>
}