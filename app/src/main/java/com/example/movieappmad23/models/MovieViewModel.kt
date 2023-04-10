package com.example.movieappmad23.models

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    fun getFavMovieList(): List<Movie>
    {
        return _movieList.filter { (it.isFavorite) }
    }

    fun getMovieById(movieID: String): Movie {
        return (_movieList.filter { it.id == movieID })[0]
    }

    fun toggleFavorite(movieID: String) {
        Log.d("toogleFavorite", "toggleFavorite was called on Movie $movieID")
        for (movie in _movieList) {
            if (movie.id == movieID) {
                Log.d("toogleFavorite", "which is ${movie.title}")
                movie.isFavorite = !movie.isFavorite
            }
        }
    }
}