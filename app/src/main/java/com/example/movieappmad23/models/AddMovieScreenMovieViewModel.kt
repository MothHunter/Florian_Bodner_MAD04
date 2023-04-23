package com.example.movieappmad23.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddMovieScreenMovieViewModel(private val repository: MovieRepository): ViewModel() {

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

    suspend fun addMovie(movie: Movie) {
        repository.add(movie)
    }
}