package com.example.noticias.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path

interface NoticiaApi {
    @GET("v1/coins")
    suspend fun getCoins(): List<NoticiasDto>

    @GET("v1/coins/{id}")
    suspend fun getCoinDetail(@Path("id") coinId: String): NoticiasDetailDto
}