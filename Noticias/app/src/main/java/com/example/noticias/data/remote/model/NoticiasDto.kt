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
    val multimedia: List<MultimediaDto>? = null,
    val uri: String,
    val item_type: String?,
    val kicker: String?,
    val material_type_facet: String?,
    val short_url: String,
    val updated_date: String,
    val created_date: String

) {
    // Converte um NoticiasDto para o modelo de Noticia
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
            multimedia = multimedia?.map { it.toMultimedia() },
            uri = uri,
            item_type = item_type ?: "Unknown",
            kicker = kicker ?: "No kicker available",
            material_type_facet = material_type_facet ?: "General",
            short_url = short_url,
            updated_date = updated_date,
            created_date = created_date
        )
    }
}
