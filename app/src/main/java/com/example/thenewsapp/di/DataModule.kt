package com.example.thenewsapp.di

import com.example.thenewsapp.repository.CRUDRepository
import com.example.thenewsapp.repository.ICRUDRepository
import com.example.thenewsapp.repository.INewsRepository
import com.example.thenewsapp.repository.NewsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule =
    module {
        factory<ICRUDRepository> { CRUDRepository(get(koinIO), get()) }
        factory<INewsRepository> { NewsRepository(get(), androidContext()) }
    }
