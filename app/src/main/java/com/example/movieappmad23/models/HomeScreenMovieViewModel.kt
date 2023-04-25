package com.example.movieappmad23.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenMovieViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movies = MutableStateFlow(listOf<Movie>())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    //fun addMovies von Lena
    suspend fun addMovies(){
        if (movies.value.none { it.title == "Avatar" }){
            getMovies().forEach{ movie -> repository.add(movie)}
        }
    }

    // called when object is created ~constructor
    init {
        Log.d("HomeScreenVM", "init started")
        viewModelScope.launch {
            Log.d("HomeScreenVM", "coroutine launched")
            addMovies()
            //Log.d("HomeScreenVM", "add movies completed")
            Log.d("HomeScreenVM", "coroutine launched")
            repository.getAllMovies().collect {
                    movieList ->
                _movies.value = movieList
            }
            Log.d("HomeScreenVM", "coroutine completed")
        }
        Log.d("HomeScreenVM", "init completed")
    }

    suspend fun toggleFavoriteState(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}