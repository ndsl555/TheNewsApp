package com.example.thenewsapp.Transaction

interface DatabaseTransactionRunner {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}
