package com.example.thenewsapp.repository

import androidx.lifecycle.LiveData
import com.example.thenewsapp.models.Article

interface ICRUDRepository {
    suspend fun upsert(article: Article): Long

    fun getFavNews(): LiveData<List<Article>>

    suspend fun deleteArticle(article: Article)
}
