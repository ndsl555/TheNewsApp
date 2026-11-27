package com.example.thenewsapp.di

import com.example.thenewsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModule: Module =
    module {
        viewModel { NewsViewModel(get(), get()) }
    }
