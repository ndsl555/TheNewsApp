package com.example.thenewsapp.di

import com.example.thenewsapp.datasource.INewsRemoteDataSource
import com.example.thenewsapp.datasource.NewsRemoteDataSource
import com.example.thenewsapp.network.NewsService
import com.example.thenewsapp.util.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

val networkModule =
    module {
        single {
            GsonBuilder()
                .setLenient()
                .create()
        }

        single(named("NewsRetrofit")) {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()
        }

        factory {
            get<Retrofit>(named("NewsRetrofit")).create(NewsService::class.java)
        }

        factory<INewsRemoteDataSource> {
            NewsRemoteDataSource(
                get(),
                get(koinIO),
            )
        }
    }
