package com.example.noticias.data.remote.api

import com.example.noticias.data.remote.model.NoticiaDetailDto
import com.example.noticias.data.remote.model.NoticiasDto
import com.example.noticias.data.remote.model.NoticiasResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NoticiaApi {
    @GET("{section}.json")
    suspend fun getNoticia(
        @Path("section") section: String = "home",
        @Query("api-key") apiKey: String = "sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0",
    ): NoticiasResponseDto

    @GET
    suspend fun getNoticiaDetail(
        @Url noticiaUrl: String,
        @Query("api-key") apiKey: String = "sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0"
    ): NoticiaDetailDto
}