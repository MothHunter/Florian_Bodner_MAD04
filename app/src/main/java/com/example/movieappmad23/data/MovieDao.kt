package com.example.movieappmad23.data

import androidx.room.*
import com.example.movieappmad23.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // CRUD functions
    @Insert
    suspend fun add(movie: Movie)     // suspend = long running function -> coroutine-thread

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): Flow<List<Movie>>     // functions working with Flow don't need "suspend"?
    // flow = observable => notified on change
    // should be used whenever data is read

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    suspend fun getAllFavorites(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id=:movieId")
    suspend fun getMovieById(movieId: String): Movie
}