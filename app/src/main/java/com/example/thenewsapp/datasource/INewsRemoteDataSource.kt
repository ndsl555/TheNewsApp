package com.example.thenewsapp.datasource

import com.example.thenewsapp.models.NewsResponse
import retrofit2.Response

interface INewsRemoteDataSource {
    suspend fun getHeadlines(
        countryCode: String,
        pageNumber: Int,
    ): Response<NewsResponse>

    suspend fun searchForNews(
        searchQuery: String,
        pageNumber: Int,
    ): Response<NewsResponse>
}
