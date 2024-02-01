package com.leondev.movie_app.movie_detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.leondev.movie_app.movie_detail.domain.model.MovieDetail
import com.leondev.movie_app.movie_list.data.remote.MovieListApi
import com.leondev.movie_app.util.convertMinuteToHour
import com.leondev.movie_app.util.extension.shimmerLoadingAnimation
import com.leondev.movie_app.util.getAverageColor
import com.leondev.movie_app.util.route.Screen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun MovieDetailScreen(navController: NavHostController, movieDetailUIState: MovieDetailUIState) {
    var dominantColor by remember {
        mutableStateOf(Color.Black)
    }

    if (movieDetailUIState.movieDetail == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
            )
        }

        return
    }

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieListApi.IMAGE_BASE_URL + MovieListApi.IMAGE_ORIGINAL + movieDetailUIState.movieDetail.poster_path)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    ).state

    Box(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
    ) {


        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .padding(14.dp)
            ) {
                Column {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White.copy(
                                alpha = 0.2f
                            )
                        ),
                        modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "",
                            tint = Color.White.copy(
                                alpha = 0.65f
                            )
                        )
                    }
                    Box(Modifier.weight(5f))
                    Row(
                        Modifier
                            .height(300.dp),
                    ) {
                        Box {
                            Card(
                                modifier = Modifier
                                    .height(300.dp)
                                    .width(200.dp)
                                    .align(Alignment.CenterEnd)
                            ) {
                                if (posterImageState is AsyncImagePainter.State.Error) {
                                    Icon(
                                        imageVector = Icons.Rounded.ImageNotSupported,
                                        contentDescription = null
                                    )
                                }

                                if (posterImageState is AsyncImagePainter.State.Loading) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .shimmerLoadingAnimation()
                                    )
                                }

                                if (posterImageState is AsyncImagePainter.State.Success) {
                                    dominantColor =
                                        posterImageState.result.drawable.toBitmap().let {
                                            getAverageColor(
                                                imageBitmap = it.asImageBitmap()
                                            )
                                        }

                                    buildMoviePoster(
                                        posterImageState.painter,
                                        movieDetailUIState.movieDetail,
                                        navController
                                    )
                                }
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Popularity",
                                fontSize = 14.sp,
                                color = Color.LightGray.copy(
                                    alpha = 0.7f
                                )
                            )
                            Text(
                                text = "${movieDetailUIState.movieDetail.popularity}"
                            )
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rating",
                                fontSize = 14.sp,
                                color = Color.LightGray.copy(
                                    alpha = 0.7f
                                )
                            )
                            Text(text = "${movieDetailUIState.movieDetail.vote_average}")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Released On",
                                fontSize = 14.sp,
                                color = Color.LightGray.copy(
                                    alpha = 0.7f
                                )
                            )
                            Text(
                                text = movieDetailUIState.movieDetail.release_date.split("-")
                                    .first()
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Box(Modifier.weight(1f))
                }
            }
            Box(
                Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
                    .weight(2f)
                    .padding(horizontal = 10.dp)
            ) {
                Column {
                    Text(
                        text = movieDetailUIState.movieDetail.title,
                        fontSize = 40.sp,
                        lineHeight = 1.em
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = dominantColor.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = movieDetailUIState.movieDetail.genres.first().name,
                                modifier = Modifier.padding(
                                    vertical = 6.dp,
                                    horizontal = 10.dp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = dominantColor.copy(alpha = 0.5f)
                            )
                        ) {
                            Text(
                                text = convertMinuteToHour(movieDetailUIState.movieDetail.runtime),
                                modifier = Modifier.padding(
                                    vertical = 6.dp,
                                    horizontal = 10.dp
                                )
                            )
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (movieDetailUIState.movieDetail.tagline.isNotEmpty()) {
                        Text(
                            text = movieDetailUIState.movieDetail.tagline,
                            fontSize = 18.sp,
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Text(
                        text = movieDetailUIState.movieDetail.overview,
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(
                            vertical = 6.dp,
                        ),
                    )
                    Spacer(modifier = Modifier.weight(3f))
                }
            }
        }
    }

}

@Composable
fun buildMoviePoster(
    painter: Painter,
    movieDetail: MovieDetail?,
    navController: NavHostController
) {
    var showImage by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(showImage) {
        delay(1.seconds)
        showImage = true
    }

    if (showImage) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentDescription = movieDetail?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.navigate(Screen.Video.route + "/${movieDetail?.id}")
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayCircleOutline,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)

                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerLoadingAnimation()
        )
    }

}
