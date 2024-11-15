package com.example.noticias.data.remote.model

import com.example.noticias.domain.model.Noticia

data class NoticiasDto(
    val title: String,
    val published_date: String,
    val url: String
) {
    fun toNoticia(): Noticia {
        return Noticia(title = title, published_date = published_date, url = url)
    }
}