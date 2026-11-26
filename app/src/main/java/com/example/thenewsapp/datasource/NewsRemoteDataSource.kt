package com.example.thenewsapp.datasource

import com.example.thenewsapp.models.NewsResponse
import com.example.thenewsapp.network.NewsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRemoteDataSource(
    private val newsService: NewsService,
    private val ioDispatcher: CoroutineDispatcher,
) : INewsRemoteDataSource {
    override suspend fun getHeadlines(
        countryCode: String,
        pageNumber: Int,
    ): Response<NewsResponse> =
        withContext(ioDispatcher) {
            val response = newsService.getHeadlines(countryCode, pageNumber)
            return@withContext response
        }

    override suspend fun searchForNews(
        searchQuery: String,
        pageNumber: Int,
    ): Response<NewsResponse> =
        withContext(ioDispatcher) {
            val response = newsService.searchForNews(searchQuery, pageNumber)
            return@withContext response
        }
}
