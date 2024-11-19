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
    fun toMultimedia() = Multimedia(
        url = url ?: "",
        format = format ?: "Unknown",
        caption = caption ?: "No caption available"
    )
}
