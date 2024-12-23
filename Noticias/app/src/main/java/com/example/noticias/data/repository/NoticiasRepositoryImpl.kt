package com.example.noticias.data.repository

import com.example.noticias.data.remote.api.NoticiaApi
import com.example.noticias.domain.model.Noticia
import com.example.noticias.domain.repository.NoticiaRepository

class NoticiasRepositoryImpl(private val api: NoticiaApi) : NoticiaRepository {
    override suspend fun getNoticias(): List<Noticia> {
        return api.getNoticia().results.map { it.toNoticia() } // Mapeia os dados recebidos para o modelo Noticia
    }

}