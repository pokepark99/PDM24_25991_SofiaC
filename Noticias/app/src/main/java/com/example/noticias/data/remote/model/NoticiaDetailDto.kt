package com.example.noticias.data.remote.model

import com.example.noticias.domain.model.NoticiaDetail

data class NoticiaDetailDto(
    val title: String,
    val abstract: String,
    val url: String,
    val section: String,
    val subsection: String,
    val byline: String,
    val published_date: String
) {
    fun toNoticiaDetail(): NoticiaDetail {
        return NoticiaDetail(
            title = title,
            url = url,
            section = section,
            subsection = subsection
        )
    }
}