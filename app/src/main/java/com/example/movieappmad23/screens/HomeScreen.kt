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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.models.*
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.widgets.HomeTopAppBar
import com.example.movieappmad23.widgets.MovieRow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController = rememberNavController()){
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MovieViewModelFactory(repository)
    val viewModel: HomeScreenMovieViewModel = viewModel(factory = factory)
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
        MainContent(modifier = Modifier.padding(padding),
            navController = navController,
            // @Leon: I'm not sure if we are supposed to pass the MovieViewModel down to MovieList,
            // but it seems cleaner than passing the movie list AND the onClick for the FavIcon
            // (which needs to access the viewModel)
            viewModel = viewModel)
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: HomeScreenMovieViewModel
) {
    MovieList(
        modifier = modifier,
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeScreenMovieViewModel
) {
    val movieState by viewModel.movies.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movieState) { movie ->
            MovieRow(
                movie,
                onItemClick = { movieId ->
                    Log.d("MovieItem", "got clicked on")
                    navController.navigate(Screen.DetailScreen.route + "/${movieId}")
                },
                onFavIconClick = {

                    coroutineScope.launch {
                        viewModel.toggleFavoriteState(movie)}
                    }
            )
        }
    }
}


