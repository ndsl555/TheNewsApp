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
import okio.IOException
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
        try {
            val response = newsRepository.searchForNews(searchQuery, searchNewsPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        } catch (e: Exception) {
            when (e) {
                is IOException -> searchNews.postValue(Resource.Error("Unable to connect"))
                else -> searchNews.postValue(Resource.Error("No signal"))
            }
        }
    }

    private suspend fun getHeadlinesInternet(countryCode: String) {
        headlines.postValue(Resource.Loading())
        try {
            val response = newsRepository.getHeadlines(countryCode, headlinesPage)
            headlines.postValue(handleHeadlinesResponse(response))
        } catch (e: Exception) {
            when (e) {
                is IOException -> headlines.postValue(Resource.Error("Unable to connect"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }

    // 1.
    // 第一次載入（或初始狀態）：
    // 如果 headlinesResponse == null（表示這是第一次抓取頭條新聞），直接把這次取得的回應 it 指定給 headlinesResponse。
    //
    // 2.
    // 後續載入更多（換頁）：
    // 如果 headlinesResponse 已經有資料，代表使用者已經載入過第一頁，現在載入的是第二頁或之後的頁面。
    // 它會取出原本快取中的舊文章列表 oldArticles（headlinesResponse?.articles）。
    // 取出這次新抓到的文章列表 newArticles（it.articles）。
    //
    // 使用 oldArticles?.addAll(newArticles) 把新文章附加到舊文章的後面，達到清單累加、無限滾動的效果。

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
