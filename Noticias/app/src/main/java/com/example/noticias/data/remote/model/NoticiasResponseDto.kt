package com.example.noticias.data.remote.model

data class NoticiasResponseDto(
    val status: String,
    val results: List<NoticiasDto>
)