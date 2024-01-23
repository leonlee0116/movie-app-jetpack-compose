package com.leondev.movie_app.presentation.movie_list_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.leondev.movie_app.presentation.movie_list_screen.component.MovieCard
import com.leondev.movie_app.presentation.movie_list_screen.state.MovieListUIEvent
import com.leondev.movie_app.presentation.movie_list_screen.state.MovieListUIState

@Composable
fun MovieListScreen(
    movieListUIState: MovieListUIState,
    navHostController: NavHostController,
    onEvent: (MovieListUIEvent) -> Unit
) {
    if (movieListUIState.popularMovieList.isEmpty() || movieListUIState.topRatedMovieList.isEmpty()) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .fillMaxSize()
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = {
                GridItemSpan(2)
            }) {
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Top Rated",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(
                        modifier = Modifier.height(150.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(movieListUIState.topRatedMovieList.size) { index ->
                            MovieCard(
                                movieListUIState.topRatedMovieList[index],
                                navHostController,
                                -1
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Popular",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                }
            }
            items(movieListUIState.popularMovieList.size) { index ->
                MovieCard(movieListUIState.popularMovieList[index], navHostController, index)
            }
        }

    }
}