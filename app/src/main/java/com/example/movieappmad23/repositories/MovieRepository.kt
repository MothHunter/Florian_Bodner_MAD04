package com.example.movieappmad23.repositories

import com.example.movieappmad23.data.MovieDao
import com.example.movieappmad23.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun add(movie: Movie) = movieDao.add(movie)

    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    fun getAllMovies() = movieDao.getAllMovies() /*: Flow<List<Movie>>
    {
        Log.d("MovieRepository", "getAllMovies called")
        var flow  = movieDao.getAllMovies()
        Log.d("MovieRepository", "flow contains ${flow.toList().size} movies")

        // if the database is empty, populate it with the predefined list
        if (flow.toList().isNullOrEmpty()) {
            val list = getMovies()
            Log.d("MovieRepository", "trying to add ${list.size} movies")
            for (movie in list) {
                add(movie)
            }
            flow  = movieDao.getAllMovies()
            Log.d("MovieRepository", "flow now contains ${flow.toList().size} movies")
        }
        return flow
    }
    */



    fun getFavoriteMovies() = movieDao.getAllFavorites()

    fun getMovieById(id: Int) : Flow<Movie?> = movieDao.getMovieById(id)
}