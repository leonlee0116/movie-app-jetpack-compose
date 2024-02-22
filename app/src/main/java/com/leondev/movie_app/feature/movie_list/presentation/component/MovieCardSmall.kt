package com.leondev.movie_app.feature.movie_list.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
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
import com.leondev.movie_app.feature.movie_list.domain.model.Movie
import com.leondev.movie_app.util.constant.BaseApi
import com.leondev.movie_app.util.getAverageColor
import com.leondev.movie_app.util.route.Screen
import java.math.RoundingMode

@Composable
fun MovieCardSmall(
    movie: Movie,
    navHostController: NavHostController,
) {
    // build movie image
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(BaseApi.IMAGE_BASE_URL + BaseApi.IMAGE_W500 + movie.poster_path)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    ).state

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    Column(
        modifier = Modifier
            .width(250.dp)
            .clickable {
                navHostController.navigate(Screen.Detail.route + "/${movie.id}")
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .height(150.dp)
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

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = dominantColor.copy(
                        alpha = 0.8f
                    )
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.padding(6.dp)
            ) {
                Text(
                    text = movie.release_date.split("-").first(),
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
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
            modifier = Modifier.height(4.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = "",
                tint = Color.Yellow,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = (movie.vote_average / 2).toBigDecimal().setScale(1, RoundingMode.HALF_UP)
                    .toString(),
                fontSize = 14.sp
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
    }


}