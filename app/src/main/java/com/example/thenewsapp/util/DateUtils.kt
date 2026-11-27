package com.example.thenewsapp.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String?.format(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        val parsed = OffsetDateTime.parse(this)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())
        parsed.format(formatter)
    } catch (_: Exception) {
        this
    }
}
