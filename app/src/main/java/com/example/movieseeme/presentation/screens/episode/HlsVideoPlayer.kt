package com.example.movieseeme.presentation.screens.episode

import android.app.Activity
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import com.example.movieseeme.data.remote.model.request.WatchingCreateRequest
import com.example.movieseeme.data.remote.model.request.WatchingUpdateRequest
import com.example.movieseeme.presentation.viewmodels.movie.EpisodeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import kotlin.math.roundToInt

@OptIn(UnstableApi::class)
@Composable
fun HlsVideoPlayer(
    modifier: Modifier = Modifier,
    movieID: String,
    interactionViewModel: InteractionViewModel,
    episodeViewModel: EpisodeViewModel
) {
    val exoPlayer = episodeViewModel.exoPlayer
    val url by episodeViewModel.currentUrl.collectAsState()
    val context = LocalContext.current // Lấy context để check PiP
    val dataMovieId by episodeViewModel.selectedDataMovie.collectAsState()
    val listMovieWatching by interactionViewModel.listMovieWatching.collectAsState()

    LaunchedEffect(url) {
//        episodeViewModel.playVideo(url)
        episodeViewModel.playVideo(exoPlayer, url)

        val existing = listMovieWatching.firstOrNull { it.dataMovieId == dataMovieId?.id }
        val progressMin = existing?.progressSeconds ?: 0

        if (progressMin > 0) {
            val progressMs = progressMin * 60 * 1000
            exoPlayer.seekTo(progressMs.toLong())
        }

    }

    DisposableEffect(Unit) {
        val activity = context as Activity

        onDispose {
            if (activity.isInPictureInPictureMode) {
                activity.finish()   // Thoát PiP window
            }


            val watchedMs = exoPlayer.currentPosition
            val watchedMin =
                (watchedMs / 1000f / 60f).roundToInt()
            val existing = listMovieWatching.firstOrNull { it.dataMovieId == dataMovieId?.id }
            if (existing != null) {
                interactionViewModel.updateWatching(
                    WatchingUpdateRequest(
                        dataMovieId = dataMovieId!!.id,
                        newProgressSeconds = watchedMin
                    )
                )
                interactionViewModel.getMoviesWatching()
            } else {
                interactionViewModel.postMovieToWatching(
                    WatchingCreateRequest(
                        movieId = movieID,
                        dataMovieId = dataMovieId!!.id,
                        progressSeconds = watchedMin
                    )
                )
                interactionViewModel.getMoviesWatching()
            }
//            episodeViewModel.clearEpisodes()
        }
    }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_PAUSE) {
                // Kiểm tra nếu đang ở chế độ PiP thì KHÔNG ĐƯỢC pause
                val activity = context as? Activity
                val isPip = activity?.isInPictureInPictureMode == true

                if (!isPip) {
                    exoPlayer.play()
                }
            } else if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                if (exoPlayer.playbackState != ExoPlayer.STATE_IDLE) {
                    // Nếu đang PiP mà quay lại full screen hoặc resume bình thường
                    exoPlayer.pause()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
        }
    )
}
