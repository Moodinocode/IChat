package com.capstone.securemessaging.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters for Room database
 * Converts complex types to/from JSON strings for storage
 */
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return if (list == null) "[]" else gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        if (value == null || value.isEmpty()) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}

