package com.leondev.movie_app.presentation.movie_list_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.leondev.movie_app.data.remote.MovieApi
import com.leondev.movie_app.domain.model.Movie
import com.leondev.movie_app.util.getAverageColor
import com.leondev.movie_app.util.route.Screen

@Composable
fun MovieCardSmall(
    movie: Movie,
    navHostController: NavHostController,
) {
    // build movie image
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + MovieApi.IMAGE_W500 + movie.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    Column(
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                navHostController.navigate(Screen.Detail.route + "/${movie.id}")
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (imageState is AsyncImagePainter.State.Error) {
                Icon(imageVector = Icons.Rounded.ImageNotSupported, contentDescription = null)
            }

            if (imageState is AsyncImagePainter.State.Success) {
                dominantColor = imageState.result.drawable.toBitmap().let {
                    getAverageColor(
                        imageBitmap = it.asImageBitmap()
                    )
                }
                Image(
                    painter = imageState.painter,
                    contentDescription = movie.title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        Text(
            text = movie.title,
            maxLines = 1,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
    }


}