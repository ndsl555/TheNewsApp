package com.example.thenewsapp.repository

import androidx.lifecycle.LiveData
import com.example.thenewsapp.datasource.ICRUDDataSource
import com.example.thenewsapp.models.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CRUDRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: ICRUDDataSource,
) : ICRUDRepository {
    override suspend fun upsert(article: Article) =
        withContext(ioDispatcher) {
            dataSource.upsert(article)
        }

    override fun getFavNews(): LiveData<List<Article>> = dataSource.getFavNews()

    override suspend fun deleteArticle(article: Article) =
        withContext(ioDispatcher) {
            dataSource.deleteArticle(article)
        }
}
