package com.example.noticias.data.remote.model

import com.example.noticias.domain.model.Multimedia

data class MultimediaDto(
    val url: String?,
    val format: String?,
    val height: Int?,
    val width: Int?,
    val type: String?,
    val subtype: String?,
    val caption: String?,
    val copyright: String?
) {
    // Converte um MultimediaDto para o modelo de Multimedia
    fun toMultimedia() = Multimedia(
        url = url ?: "", //String vazia como default
        format = format ?: "Unknown",
        caption = caption ?: "No caption available"
    )
}
