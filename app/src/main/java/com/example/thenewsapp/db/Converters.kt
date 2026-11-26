package com.example.thenewsapp.db

import androidx.room.TypeConverter
import com.example.thenewsapp.models.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String = source.name

    @TypeConverter
    fun toSource(name: String): Source = Source(name, name)
}
