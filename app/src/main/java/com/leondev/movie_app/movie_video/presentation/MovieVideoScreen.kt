package com.leondev.movie_app.movie_video.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.leondev.movie_app.movie_video.domain.model.MovieVideoResults
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.Locale

@Composable
fun MovieVideoScreen(navController: NavHostController, movieVideoResult: List<MovieVideoResults>) {
    if (movieVideoResult.isEmpty()) {
        CircularProgressIndicator()
        return
    }

    val trailerVideo = remember {
        movieVideoResult.filter {
            it.site.toLowerCase(Locale("en")).contains("youtube")
                    && it.type.toLowerCase().contains("trailer")
        }.map {
            it
        }.toList()
    }
    val currentVideoId = remember {
        trailerVideo.first().key
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        YoutubeScreen(currentVideoId)
    }

}

@Composable
fun YoutubeScreen(
    videoId: String,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    AndroidView(factory = {
        var view = YouTubePlayerView(it)
        val fragment = view.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        )
        view
    })
}