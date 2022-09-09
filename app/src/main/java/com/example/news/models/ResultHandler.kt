package com.example.news.models

import android.util.Log

sealed class ResultHandler<out T> {
    class Success<out T>(val result: T) : ResultHandler<T>() {
        init {
            Log.d("Success:", "$result")
        }
    }
    object Loading : ResultHandler<Nothing>()
    class Failure(exception: Throwable, val message: String = exception.message.toString()) : ResultHandler<Nothing>(){
        init {
            Log.d("Error:","$exception")
        }
    }
}