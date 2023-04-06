package com.example.movieappmad23

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieappmad23.navigation.Navigation
import com.example.movieappmad23.ui.theme.MovieAppMAD23Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD23Theme {
                Navigation()
            }
        }
        Log.i("MainActivity","I'm created")
    }
    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "I'm started")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "I'm resumed")
    }
    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "I'm paused")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "I'm stopped")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "I'm restarted")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity","I'm destroyed")
    }

}

