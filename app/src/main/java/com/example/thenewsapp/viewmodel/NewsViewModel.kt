package com.example.thenewsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thenewsapp.models.Article
import com.example.thenewsapp.models.NewsResponse
import com.example.thenewsapp.repository.ICRUDRepository
import com.example.thenewsapp.repository.INewsRepository
import com.example.thenewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: INewsRepository,
    private val crudRepository: ICRUDRepository,
) : ViewModel() {
    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newsSearchQuery: String? = null
    var oldSearchQuery: String? = null

    init {
        getHeadlines("us")
    }

    fun searchNews(searchQuery: String) =
        viewModelScope.launch {
            searchNewsInternet(searchQuery)
        }

    fun getHeadlines(countryCode: String) =
        viewModelScope.launch {
            getHeadlinesInternet(countryCode)
        }

    private suspend fun searchNewsInternet(searchQuery: String) {
        newsSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchForNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private suspend fun getHeadlinesInternet(countryCode: String) {
        headlines.postValue(Resource.Loading())
        val response = newsRepository.getHeadlines(countryCode, headlinesPage)
        headlines.postValue(handleHeadlinesResponse(response))
    }

    private fun handleHeadlinesResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                headlinesPage++
                if (headlinesResponse == null) {
                    headlinesResponse = it
                } else {
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(headlinesResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                if (searchNewsResponse == null || newsSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newsSearchQuery
                    searchNewsResponse = it
                } else {
                    searchNewsPage++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFav(article: Article) =
        viewModelScope.launch {
            crudRepository.upsert(article)
        }

    fun getFavNews() = crudRepository.getFavNews()

    fun deleteArticle(article: Article) =
        viewModelScope.launch {
            crudRepository.deleteArticle(article)
        }
}
