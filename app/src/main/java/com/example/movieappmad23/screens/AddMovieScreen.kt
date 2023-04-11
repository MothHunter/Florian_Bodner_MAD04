package com.example.movieappmad23.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieappmad23.R
import com.example.movieappmad23.models.*
import com.example.movieappmad23.widgets.SimpleTopAppBar

@Composable
fun AddMovieScreen(navController: NavController, movieViewModel: MovieViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding),
            validateField = { field: AddMovieFields, fieldValue: String
                ->
                movieViewModel.validateField(field, fieldValue)
            },
            addMovie = { movie: Movie ->
                movieViewModel.addMovie(movie)
                navController.popBackStack()
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    validateField: (field: AddMovieFields, fieldValue: String) -> Boolean,
    addMovie: (movie: Movie) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
            .clickable(onClick = { focusManager.clearFocus() })
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            var title by remember {
                mutableStateOf("")
            }

            var titleError by remember {
                mutableStateOf(true)
            }

            var year by remember {
                mutableStateOf("")
            }

            var yearError by remember {
                mutableStateOf(true)
            }

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }

            var genreError by remember {
                mutableStateOf(true)
            }

            var director by remember {
                mutableStateOf("")
            }

            var directorError by remember {
                mutableStateOf(true)
            }

            var actors by remember {
                mutableStateOf("")
            }

            var actorsError by remember {
                mutableStateOf(true)
            }

            var plot by remember {
                mutableStateOf("")
            }

            // currently there can be no plot error, but maybe in the future??
            var plotError by remember {
                mutableStateOf(!validateField(AddMovieFields.PLOT, plot))
            }

            var rating by remember {
                mutableStateOf("")
            }

            var ratingError by remember {
                mutableStateOf(true)
            }

            var isEnabledSaveButton: Boolean =
                !(titleError || yearError || genreError || directorError ||
                        actorsError || plotError || ratingError)



            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    title = it
                    titleError = !validateField(AddMovieFields.TITLE, title)
                },
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = titleError,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    year = it
                    yearError = !validateField(AddMovieFields.YEAR, year)

                },
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = yearError,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6,
                color = if (genreError) Color.Red else Color.Black
            )

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)
            ) {
                items(genreItems) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            genreItems = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                            genreError = !validateField(AddMovieFields.GENRES,
                                genreItems.filter { it.isSelected }.toString()
                            )
                            // since we only need to check if at least one genre is selected,
                            // this should do the trick -> empty string if none selected??
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    director = it
                    directorError = !validateField(AddMovieFields.DIRECTOR, director)
                },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = directorError,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    actors = it
                    actorsError = !validateField(AddMovieFields.ACTORS, actors)
                },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = actorsError,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = {
                    plot = it
                    plotError = !validateField(AddMovieFields.PLOT, plot)
                },
                label = {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(R.string.enter_plot)
                    )
                },
                isError = plotError,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    rating = if (it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                    ratingError = !validateField(AddMovieFields.RATING, rating)
                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = ratingError,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )

            Button(
                enabled = isEnabledSaveButton,
                onClick = {
                    addMovie(
                        Movie(
                            id = "temp",
                            title = title,
                            year = year,
                            genre = genres,
                            director = director,
                            actors = actors,
                            plot = plot,
                            images = listOf("https://st.depositphotos.com/2934765/53192/v/600/depositphotos_531920820-stock-illustration-photo-available-vector-icon-default.jpg"),
                            rating = rating.toFloat()
                        )
                    )

                }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}