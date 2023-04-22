package com.example.movieappmad23.utils

import androidx.room.TypeConverter
import com.example.movieappmad23.models.Genre

class CustomConverters {

    @TypeConverter
    fun listToString(list: List<String>) = list.joinToString(",")

    @TypeConverter
    fun stringToList(string: String) = string.split(",").map{it.trim()}

    @TypeConverter
    fun genreListToString(list: List<Genre>) = list.joinToString(",")

    @TypeConverter
    fun stringToGenreList(listString: String) : List<Genre> {
        val stringList = listString.split(",")
        val genreList = emptyList<Genre>().toMutableList()
        for (string in stringList) {
            genreList.add(Genre.valueOf(string))
        }
        return genreList
    }
}