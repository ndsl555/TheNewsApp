package com.example.thenewsapp.Transaction

import androidx.room.withTransaction
import com.example.thenewsapp.db.ArticleRoomDatabase

class RoomTransactionRunner(
    private val db: ArticleRoomDatabase,
) : DatabaseTransactionRunner {
    override suspend fun <T> invoke(block: suspend () -> T): T =
        db.withTransaction {
            block()
        }
}
