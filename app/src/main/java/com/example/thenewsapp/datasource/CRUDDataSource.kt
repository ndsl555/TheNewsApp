package com.example.thenewsapp.datasource

import androidx.lifecycle.LiveData
import com.example.thenewsapp.db.ArticleDao
import com.example.thenewsapp.models.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CRUDDataSource(
    private val dao: ArticleDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ICRUDDataSource {
    override suspend fun upsert(article: Article) =
        withContext(ioDispatcher) {
            dao.upsert(article)
        }

    override fun getFavNews(): LiveData<List<Article>> = dao.getAllArticles()

    override suspend fun deleteArticle(article: Article) {
        withContext(ioDispatcher) {
            dao.deleteArticle(article)
        }
    }
}
