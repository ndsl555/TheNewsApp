package com.example.thenewsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thenewsapp.models.Article

@Database(
    entities = [
        Article::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class ArticleRoomDatabase :
    RoomDatabase(),
    ArticleDataBase {
    companion object {
        const val DATABASE_NAME = "article_db.db"
    }
}
