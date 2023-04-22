package com.example.movieappmad23.repositories

import androidx.compose.runtime.rememberCoroutineScope
import com.example.movieappmad23.data.MovieDao
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.models.getMovies
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun add(movie: Movie) = movieDao.add(movie)

    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    suspend fun getAllMovies(): Flow<List<Movie>> {
        var flow  = movieDao.getAllMovies()

        // if the database is empty, populate it with the predefined list
        if (flow.toList().isNullOrEmpty()) {
            val list = getMovies()
            for (movie in list) {
                add(movie)
            }
            flow  = movieDao.getAllMovies()
        }
        return flow
    }

    suspend fun getFavoriteMovies() = movieDao.getAllFavorites()

    suspend fun getMovieById(id: String) = movieDao.getMovieById(id)
}