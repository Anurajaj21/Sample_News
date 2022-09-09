package com.example.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.models.ResultHandler
import com.example.news.models.NewsModel
import com.example.news.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = MainRepository()

    private val _responseLiveData = MutableLiveData<ResultHandler<ArrayList<NewsModel>?>>()
    val responseLiveData: LiveData<ResultHandler<ArrayList<NewsModel>?>> = _responseLiveData

    private var newsList : ArrayList<NewsModel> = ArrayList()

    fun getNews() = viewModelScope.launch {
        try {
            val res = repo.getNews().body()
            _responseLiveData.postValue(ResultHandler.Success(res))
        } catch (e: Exception) {
            _responseLiveData.postValue(ResultHandler.Failure(e))
        }
    }

    fun setNewsList(list : ArrayList<NewsModel>){
        newsList = list
    }

    fun getNewsList() : ArrayList<NewsModel> = newsList
}