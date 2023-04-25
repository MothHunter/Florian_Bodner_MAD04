package com.example.movieappmad23.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad23.repositories.MovieRepository

// because we need to pass parameters to our MovieViewModel we need this factory class for it
class MovieViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenMovieViewModel::class.java)) {
            return HomeScreenMovieViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteScreenMovieViewModel::class.java)) {
            return FavoriteScreenMovieViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(AddMovieScreenMovieViewModel::class.java)) {
            return AddMovieScreenMovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}