package com.example.movieappmad23.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.models.DetailScreenMovieViewModel
import com.example.movieappmad23.models.DetailScreenMovieViewModelFactory
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.widgets.HorizontalScrollableImageView
import com.example.movieappmad23.widgets.MovieRow
import com.example.movieappmad23.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    navController: NavController,
    movieId: Int
) {
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = DetailScreenMovieViewModelFactory(repository, movieId)
    val viewModel: DetailScreenMovieViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    var movie = viewModel.movieState.collectAsState()


    // needed for show/hide snackbar
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

    Scaffold(
        scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = movie.value.title)
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding), movie.value,
            onFavIconClick = {
                coroutineScope.launch {
                    viewModel.toggleFavoriteState(movie.value)
                }
            })

    }
}


@Composable
fun MainContent(modifier: Modifier = Modifier, movie: Movie, onFavIconClick: (Movie) -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(movie, onFavIconClick = onFavIconClick)

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie)
        }
    }
}