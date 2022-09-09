package com.example.news.models

import java.io.Serializable

data class NewsModel(
    val title: String,
    val description: String,
    val urlToImage: String,
    val content: String
): Serializable
