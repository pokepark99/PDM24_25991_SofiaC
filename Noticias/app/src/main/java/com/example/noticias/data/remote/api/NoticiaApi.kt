package com.example.noticias.data.remote.api

import com.example.noticias.data.remote.model.NoticiaDetailDto
import com.example.noticias.data.remote.model.NoticiasDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface NoticiaApi {

    @GET("top")
    @Headers(
        "api_token: sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0",
        "api_secret: 3wkrijGFwEL1invk"
    )
    suspend fun getNoticia(): List<NoticiasDto>

    @GET("top")
    @Headers(
        "api_token: sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0",
        "api_secret: 3wkrijGFwEL1invk"
    )
    suspend fun getNoticiaDetail(
        @Path("home.json") noticiaUrl: String
    ): NoticiaDetailDto
}