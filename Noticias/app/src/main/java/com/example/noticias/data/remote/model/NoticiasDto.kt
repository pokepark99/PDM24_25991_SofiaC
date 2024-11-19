package com.example.noticias.data.remote.model

import com.example.noticias.domain.model.Noticia

data class NoticiasDto(
    val title: String,
    val published_date: String,
    val url: String,
    val abstract: String,
    val section: String,
    val subsection: String,
    val byline: String,
    val desFacet: List<String> = emptyList(),
    val orgFacet: List<String> = emptyList(),
    val perFacet: List<String> = emptyList(),
    val geoFacet: List<String> = emptyList(),
    val multimedia: List<MultimediaDto>? = null
) {
    fun toNoticia(): Noticia {
        return Noticia(
            title = title,
            published_date = published_date,
            url = url,
            abstract = abstract,
            section = section,
            subsection = subsection,
            byline = byline,
            desFacet = desFacet ?: emptyList(),
            orgFacet = orgFacet ?: emptyList(),
            perFacet = perFacet ?: emptyList(),
            geoFacet = geoFacet ?: emptyList(),
            multimedia = multimedia?.map { it.toMultimedia() }
        )
    }
}
