package com.example.noticias.domain.model

data class Noticia(
    val title: String,
    val publishedDate: String?,
    val url: String,
    val abstract: String,
    val section: String,
    val subsection: String,
    val byline: String,
    val desFacet: List<String> = emptyList(),
    val orgFacet: List<String> = emptyList(),
    val perFacet: List<String> = emptyList(),
    val geoFacet: List<String> = emptyList(),
    val multimedia: List<Multimedia>? = null
)