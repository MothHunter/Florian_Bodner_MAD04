package com.example.movieappmad23.models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailScreenMovieViewModel(private val repository: MovieRepository, private val movieId: Int): ViewModel() {
    val movieState = MutableStateFlow(Movie(0, "movie not found", "", emptyList(), "", "","", emptyList(), 1f, false))
    init {
        viewModelScope.launch {
            repository.getMovieById(movieId)
                .collect{ movie ->
                    movie?.let {
                        movieState.value = movie
                    }
                }
        }
    }
    /*
    suspend fun getMovieById(movieID: Int): Movie {
        return repository.getMovieById(movieID)
    }

     */

    suspend fun toggleFavoriteState(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}