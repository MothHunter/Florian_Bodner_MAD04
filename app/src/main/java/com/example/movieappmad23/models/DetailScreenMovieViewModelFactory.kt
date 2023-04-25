package com.example.movieappmad23.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad23.repositories.MovieRepository

// because we need to pass parameters to our MovieViewModel we need this factory class for it
class DetailScreenMovieViewModelFactory(private val repository: MovieRepository, private val movieId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailScreenMovieViewModel::class.java)) {
            return DetailScreenMovieViewModel(repository, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}