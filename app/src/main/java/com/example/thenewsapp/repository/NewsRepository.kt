package com.example.thenewsapp.repository

import com.example.thenewsapp.datasource.INewsRemoteDataSource
import com.example.thenewsapp.models.NewsResponse
import retrofit2.Response

class NewsRepository(
    private val newsRemoteDataSource: INewsRemoteDataSource,
) : INewsRepository {
    override suspend fun getHeadlines(
        countryCode: String,
        pageNumber: Int,
    ): Response<NewsResponse> = newsRemoteDataSource.getHeadlines(countryCode, pageNumber)

    override suspend fun searchForNews(
        searchQuery: String,
        pageNumber: Int,
    ): Response<NewsResponse> = newsRemoteDataSource.searchForNews(searchQuery, pageNumber)
}
