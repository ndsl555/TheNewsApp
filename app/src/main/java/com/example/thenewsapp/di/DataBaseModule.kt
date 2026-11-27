package com.example.thenewsapp.di

import androidx.room.Room
import com.example.thenewsapp.Transaction.DatabaseTransactionRunner
import com.example.thenewsapp.Transaction.RoomTransactionRunner
import com.example.thenewsapp.datasource.CRUDDataSource
import com.example.thenewsapp.datasource.ICRUDDataSource
import com.example.thenewsapp.db.ArticleDataBase
import com.example.thenewsapp.db.ArticleRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule =
    module {
        single {
            val builder =
                Room.databaseBuilder(
                    androidContext(),
                    ArticleRoomDatabase::class.java,
                    ArticleRoomDatabase.DATABASE_NAME,
                )
            builder
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<ArticleRoomDatabase>() as ArticleDataBase }
        single<DatabaseTransactionRunner> { RoomTransactionRunner(get()) }

        // Dao
        factory { get<ArticleRoomDatabase>().getArticleDao() }

        // DataSource
        factory<ICRUDDataSource> { CRUDDataSource(get(), get(koinIO)) }
    }
