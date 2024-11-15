package com.example.noticias.data.remote

import com.example.noticias.data.remote.api.NoticiaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    val api: NoticiaApi by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoticiaApi::class.java)
    }
}