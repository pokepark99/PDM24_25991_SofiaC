package com.example.noticias.data.remote.api

import com.example.noticias.data.remote.model.NoticiasResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticiaApi {
    @GET("{section}.json")
    suspend fun getNoticia(
        @Path("section") section: String = "home",
        @Query("api-key") apiKey: String = "sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0",
    ): NoticiasResponseDto
}