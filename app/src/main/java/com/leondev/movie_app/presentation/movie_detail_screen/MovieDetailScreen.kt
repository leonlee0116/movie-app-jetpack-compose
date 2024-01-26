package com.leondev.movie_app.presentation.movie_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.leondev.movie_app.data.remote.MovieApi
import com.leondev.movie_app.presentation.viewmodel.MovieDetailViewModel
import com.leondev.movie_app.util.convertMinuteToHour
import com.leondev.movie_app.util.getAverageColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(navController: NavHostController) {
    val movieDetailListViewModel = hiltViewModel<MovieDetailViewModel>()
    val movieDetailUIState by movieDetailListViewModel.movieDetailUIState.collectAsState()


    var dominantColor by remember {
        mutableStateOf(Color.Black)
    }

    Box(Modifier.fillMaxSize()) {
        movieDetailUIState.movieDetail?.let { movieDetail ->
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(MovieApi.IMAGE_BASE_URL + MovieApi.IMAGE_ORIGINAL + movieDetail.backdrop_path)
                    .size(Size.ORIGINAL)
                    .build()
            ).state

            if (imageState is AsyncImagePainter.State.Error) {
                Icon(
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = null
                )
            }

            if (imageState is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .fillMaxSize()
                )
            }

            if (imageState is AsyncImagePainter.State.Success) {
                dominantColor = imageState.result.drawable.toBitmap().let {
                    getAverageColor(
                        imageBitmap = it.asImageBitmap()
                    )
                }
                Image(
                    painter = imageState.painter,
                    contentDescription = movieDetail.title,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxSize()
                )

                Column {
                    Box(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black)
                                )
                            )
                    )
                }
            }
            Column {
                Box(
                    modifier = Modifier.weight(2f)
                ) {

                    Column {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) { }
                        Box(
                            modifier = Modifier
//                                .background(
//                                    Brush.verticalGradient(
//                                        colors = listOf(Color.Transparent, Color.Black)
//                                    )
//                                )
                                .fillMaxWidth()
                                .weight(2f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(10.dp)
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = dominantColor.copy(alpha = 0.5f)
                                        )
                                    ) {
                                        Text(
                                            text = movieDetail.genres.first().name,
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
                                            text = convertMinuteToHour(movieDetail.runtime),
                                            modifier = Modifier.padding(
                                                vertical = 6.dp,
                                                horizontal = 10.dp
                                            )
                                        )
                                    }

                                }
                                Text(
                                    text = movieDetail.original_title,
                                    fontSize = 40.sp,
                                    lineHeight = 1.em
                                )
                                Text(
                                    text = movieDetail.tagline,
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = movieDetail.overview,
                        modifier = Modifier.padding(
                            vertical = 6.dp,
                            horizontal = 10.dp
                        )
                    )
                }
            }
        } ?: run {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .fillMaxSize()
            )
        }
    }

}