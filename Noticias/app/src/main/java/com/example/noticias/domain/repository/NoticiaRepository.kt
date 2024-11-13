package com.example.noticias.domain.repository

import com.example.noticias.domain.model.Noticia
import com.example.noticias.domain.model.NoticiaDetail

interface NoticiaRepository {
    suspend fun getNoticias(): List<Noticia>
    suspend fun getNoticiaDetail(noticiaUrl:String): NoticiaDetail
}