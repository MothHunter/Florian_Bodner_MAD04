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

    fun validateField(field: AddMovieFields, fieldValue: String): Boolean {

        if (field == AddMovieFields.PLOT) {
            return true
            // there are no requirements for the plot field, other than it being
            // a string. And since anything can be a string, there is nothing to
            // validate(??)
        }

        if (field == AddMovieFields.RATING)
        {
            val number = fieldValue.toDoubleOrNull()
            if (number != null && number >= 0 && number <= 10) {
                return true
                // checking if rating is a number and within reasonable range
            }
            return false
        }

        return fieldValue.isNotEmpty()
            // no field other than plot may be left empty

    }

    fun addMovie(movie: Movie) {
        _movieList.add(movie.copy(id = "new${movieList.size + 1}"))
    }
}