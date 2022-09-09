package com.example.news.repository

import com.example.draganimation.networks.Retrofit
import com.example.news.models.NewsModel
import com.example.news.networks.Network
import retrofit2.Response
import java.util.ArrayList

class MainRepository {

    private val retrofit = Retrofit.getClient().create(Network::class.java)

    suspend fun getNews(): Response<ArrayList<NewsModel>> = retrofit.getNews()
}