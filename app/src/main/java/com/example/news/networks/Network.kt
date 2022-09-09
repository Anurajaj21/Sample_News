package com.example.news.networks

import com.example.news.models.NewsModel
import retrofit2.Response
import retrofit2.http.GET

interface Network {

    @GET("api/1017660638595661824")
    suspend fun getNews() : Response<ArrayList<NewsModel>>
}