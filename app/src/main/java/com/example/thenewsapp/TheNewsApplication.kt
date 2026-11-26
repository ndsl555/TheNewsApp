package com.example.thenewsapp

import android.app.Application
import com.example.thenewsapp.di.databaseModule
import com.example.thenewsapp.di.ioDispatcherModule
import com.example.thenewsapp.di.networkModule
import com.example.thenewsapp.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TheNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TheNewsApplication)
            modules(
                listOf(
                    databaseModule,
                    viewModule,
                    ioDispatcherModule,
                    networkModule,
                ),
            )
        }
    }
}
