package com.example.thenewsapp.datasource

import androidx.lifecycle.LiveData
import com.example.thenewsapp.models.Article

interface ICRUDDataSource {
    suspend fun upsert(article: Article): Long

    fun getFavNews(): LiveData<List<Article>>

    suspend fun deleteArticle(article: Article)
}
