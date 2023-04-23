package com.example.movieappmad23.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad23.models.AddMovieScreenMovieViewModel
import com.example.movieappmad23.screens.*
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController)
        }

        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController)
        }

        composable(Screen.AddMovieScreen.route) {
            AddMovieScreen(navController = navController)
        }

        // build a route like: root/detail-screen/id=34
        composable(
            route = Screen.DetailScreen.route + "/{movieId}",
            arguments = listOf(
                navArgument("movieId"){type = NavType.StringType },
            )
        ){
            var movieId = it.arguments!!.getString("movieId")?.toInt()
            DetailScreen(navController = navController, movieId = movieId!!)
        }
    }
}