package com.example.thenewsapp.repository

import android.content.Context
import com.example.thenewsapp.datasource.INewsRemoteDataSource
import com.example.thenewsapp.models.NewsResponse
import com.example.thenewsapp.util.NetworkUtils
import retrofit2.Response
import java.io.IOException

class NewsRepository(
    private val newsRemoteDataSource: INewsRemoteDataSource,
    private val context: Context,
) : INewsRepository {
    override suspend fun getHeadlines(
        countryCode: String,
        pageNumber: Int,
    ): Response<NewsResponse> {
        if (!NetworkUtils.hasInternetConnection(context)) {
            throw IOException("No internet connection")
        }
        return newsRemoteDataSource.getHeadlines(countryCode, pageNumber)
    }

    override suspend fun searchForNews(
        searchQuery: String,
        pageNumber: Int,
    ): Response<NewsResponse> {
        if (!NetworkUtils.hasInternetConnection(context)) {
            throw IOException("No internet connection")
        }
        return newsRemoteDataSource.searchForNews(searchQuery, pageNumber)
    }
}
