package com.example.noticias.data.remote.api

import com.example.noticias.data.remote.model.NoticiaDetailDto
import com.example.noticias.data.remote.model.NoticiasDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NoticiaApi {
    @GET("top?api_token=sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0")
    suspend fun getNoticia(): List<NoticiasDto>

    @GET("top?api_token=sOoy4VCUGP2Ocbl6r0zVAihYGhZMSDz0")
    suspend fun getNoticiaDetail(
        @Path("/home.json") noticiaUrl: String): NoticiaDetailDto
}