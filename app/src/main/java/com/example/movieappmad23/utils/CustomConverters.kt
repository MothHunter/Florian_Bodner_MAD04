package com.example.movieappmad23.utils

import androidx.room.TypeConverter

class CustomConverters {

    @TypeConverter
    fun listToString(list: List<String>) = list.joinToString(",")

    @TypeConverter
    fun stringToList(string: String) = string.split(",").map{it.trim()}
}