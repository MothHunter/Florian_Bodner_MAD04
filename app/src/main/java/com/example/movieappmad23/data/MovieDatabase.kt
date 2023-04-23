package com.example.movieappmad23.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.utils.CustomConverters

@Database(
    entities = [Movie::class/* add all tables here */],
    version = 1,
    exportSchema = false
)
@TypeConverters(CustomConverters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    // singleton pattern
    companion object{
        @Volatile   // = var Instance is never cached => thread safety
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance?: synchronized(this) {
                // synchronized => cannot be accessed by more than one thread at a time
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it   // scope function for setting scope (=???)
                        Log.d("MovieDatabase", "created")
                    }
            }
        }
    }
}