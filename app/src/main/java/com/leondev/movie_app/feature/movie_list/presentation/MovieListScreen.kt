package com.leondev.movie_app.feature.movie_list.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.leondev.movie_app.feature.movie_list.data.remote.MovieListApi
import com.leondev.movie_app.feature.movie_list.presentation.component.MovieCard
import com.leondev.movie_app.feature.movie_list.presentation.component.MovieCardSmall
import com.leondev.movie_app.feature.movie_list.presentation.viewmodel.MovieListUIEvent
import com.leondev.movie_app.feature.movie_list.presentation.viewmodel.MovieListUIState
import com.leondev.movie_app.util.constant.BaseApi
import com.leondev.movie_app.util.extension.shimmerLoadingAnimation
import com.leondev.movie_app.util.route.Screen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private const val MAIN_POSTER_HEIGHT = 400

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
        var randomMovie by remember {
            mutableStateOf(movieListUIState.popularMovieList.random())
        }
        val posterImageState = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(BaseApi.IMAGE_BASE_URL + BaseApi.IMAGE_ORIGINAL + randomMovie.backdrop_path)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .build()
        ).state
        var showPoster by remember {
            mutableStateOf(false)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {

                Box(
                    Modifier.fillMaxWidth()
                ) {
                    if (posterImageState is AsyncImagePainter.State.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(MAIN_POSTER_HEIGHT.dp)
                                .shimmerLoadingAnimation()
                        )
                    }

                    if (posterImageState is AsyncImagePainter.State.Success) {
                        LaunchedEffect(showPoster) {
                            delay(500.milliseconds)
                            showPoster = true
                        }
                        if (showPoster) {
                            Image(
                                painter = posterImageState.painter,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(MAIN_POSTER_HEIGHT.dp)
                                    .fillMaxWidth()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(MAIN_POSTER_HEIGHT.dp)
                                    .shimmerLoadingAnimation()
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MAIN_POSTER_HEIGHT.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black,
                                        Color.Black.copy(alpha = 0.1f),
                                        MaterialTheme.colorScheme.background
                                    ),
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Text(
                                text = randomMovie.original_language.toUpperCase(),
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                    .padding(horizontal = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = randomMovie.release_date.split("-").first(),
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier
                                    .border(
                                        width = 1.dp,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                    .padding(horizontal = 4.dp)
                            )
                        }
                        Text(
                            text = randomMovie.title,
                            fontSize = 40.sp,
                            lineHeight = 1.1.em,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(6.dp))
                        Row {
                            Button(
                                onClick = {
                                    navHostController.navigate(Screen.Video.route + "/${randomMovie.id}")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White.copy(alpha = 0.1f)
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.PlayArrow,
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Trailer",
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.width(10.dp))
                            Button(
                                onClick = {
                                    navHostController.navigate(Screen.Detail.route + "/${randomMovie.id}")

                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White.copy(alpha = 0.1f)
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.ZoomIn,
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Details",
                                        color = Color.White
                                    )
                                }
                            }
//                            IconButton(
//                                onClick = {
//                                    randomMovie = movieListUIState.popularMovieList.random()
//                                }
//                            ) {
//                                Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
//                            }
                        }
                    }
                }
            }

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
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(movieListUIState.topRatedMovieList.size) { index ->
                            MovieCardSmall(
                                movieListUIState.topRatedMovieList[index],
                                navHostController,
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