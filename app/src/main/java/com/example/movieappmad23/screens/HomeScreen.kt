package com.example.movieappmad23.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.models.MovieViewModel
import com.example.movieappmad23.models.getMovies
import com.example.movieappmad23.widgets.HomeTopAppBar
import com.example.movieappmad23.widgets.MovieRow

@Composable
fun HomeScreen(navController: NavController = rememberNavController(), movieViewModel: MovieViewModel){
    Scaffold(topBar = {
        HomeTopAppBar(
            title = "Home",
            menuContent = {
                DropdownMenuItem(onClick = { navController.navigate(Screen.AddMovieScreen.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Movie", modifier = Modifier.padding(4.dp))
                        Text(text = "Add Movie", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
                DropdownMenuItem(onClick = { navController.navigate(Screen.FavoriteScreen.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", modifier = Modifier.padding(4.dp))
                        Text(text = "Favorites", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
            }
        )
    }) { padding ->
        MainContent(modifier = Modifier.padding(padding),navController = navController, movieViewModel = movieViewModel)
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    MovieList(
        modifier = modifier,
        navController = navController,
        movieViewModel = movieViewModel
    )
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movieViewModel.movieList) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    Log.d("MovieItem", "got clicked on")
                    navController.navigate(Screen.DetailScreen.withId(movieId))
                },
                onFavIconClick = {movieID ->
                    Log.d("FavIcon", "got clicked on")
                    movieViewModel.toggleFavorite(movieID)}
            )
        }
    }
}


