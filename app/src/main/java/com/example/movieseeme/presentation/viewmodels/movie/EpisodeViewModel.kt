package com.example.movieseeme.presentation.viewmodels.movie

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.example.movieseeme.R
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.model.movie.DataMovie
import com.example.movieseeme.domain.model.movie.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri

@UnstableApi
@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl,
    @param:ApplicationContext private val context: Context
) : ViewModel() {
    private val _episodeState = MutableStateFlow(AppState())
    val episodeState: StateFlow<AppState> = _episodeState.asStateFlow()

    private val _episode = MutableStateFlow<List<Episode>>(emptyList())
    val episode: StateFlow<List<Episode>> = _episode.asStateFlow()

    private val _selectedDataMovie = MutableStateFlow<DataMovie?>(null)
    val selectedDataMovie: StateFlow<DataMovie?> = _selectedDataMovie.asStateFlow()

    private val _currentUrl = MutableStateFlow("")
    val currentUrl: StateFlow<String> = _currentUrl.asStateFlow()

    fun clearEpisodes() {
        _episode.value = emptyList()
        _selectedDataMovie.value = null
        _currentUrl.value = ""
    }

    fun selectEpisode(dataMovie: DataMovie) {
        _selectedDataMovie.value = dataMovie
        _currentUrl.value = dataMovie.linkM3u8
        Log.d("EpisodeViewModel", "Selected episode: $dataMovie")
    }

    fun clearMessage() {
        _episodeState.value = AppState(success = false, message = "")
    }

    fun getEpisodes(movieId: String, dataMovieId: String) {
        viewModelScope.launch {
            _episodeState.value = AppState(isLoading = true)
            when (val result = movieRepositoryImpl.getEpisodes(movieId = movieId)) {
                is ApiResult.Success -> {
                    _episode.value = result.data.result

                    if (dataMovieId.isBlank()) {
                        val firstEpisode =
                            result.data.result.firstOrNull()?.dataMovie?.firstOrNull()
                        _episodeState.value = AppState(success = true, isLoading = false)
                        firstEpisode?.let {
                            _selectedDataMovie.value = it
                            _currentUrl.value = it.linkM3u8
                        }
                    } else {
                        _episodeState.value = AppState(success = true, isLoading = false)
                        val targetEpisode = result.data.result
                            .flatMap { it.dataMovie }
                            .firstOrNull { it.id == dataMovieId }
                        targetEpisode?.let {
                            _selectedDataMovie.value = it
                            _currentUrl.value = it.linkM3u8
                        }

                    }
                }

                is ApiResult.Error -> {
//
                }
            }
        }
    }

    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }

    private var mediaSession: MediaSession? = null
    var playerNotificationManager: PlayerNotificationManager? = null

    init {
            mediaSession = MediaSession.Builder(context, exoPlayer).build()
            setupNotification()
    }

    @OptIn(UnstableApi::class)
    private fun setupNotification() {
        val channelId = "movie_playback_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Movie Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val descriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun getCurrentContentTitle(player: Player): CharSequence {
                return player.mediaMetadata.title ?: "Đang xem phim"
            }

            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                return null
            }

            override fun getCurrentContentText(player: Player): CharSequence {
                return player.mediaMetadata.artist ?: "MovieSeeMe"
            }

            override fun getCurrentLargeIcon(
                player: Player,
                callback: PlayerNotificationManager.BitmapCallback
            ): Bitmap? {
                return null
            }
        }

        playerNotificationManager =
            PlayerNotificationManager.Builder(context, 112, channelId)
                .setMediaDescriptionAdapter(descriptionAdapter)
                .setSmallIconResourceId(R.drawable.ic_launcher_foreground)
                .build()

        playerNotificationManager?.setPlayer(exoPlayer)

    }


    @SuppressLint("UseKtx")
    @OptIn(UnstableApi::class)
    fun playVideo(exoPlayer: ExoPlayer,url: String) {
        if (url.isNotBlank()) {
//            val currentMediaId = exoPlayer.currentMediaItem?.mediaId
//            if (currentMediaId == url) return

            if (url.isBlank()) {
                exoPlayer.stop()
                return
            }
            val currentTitle = _selectedDataMovie.value?.name ?: "Đang phát"

            val metadata = MediaMetadata.Builder()
                .setTitle(currentTitle)
                .setArtist(_selectedDataMovie.value?.filename)
                .build()

            val mediaItem = MediaItem.Builder()
                .setUri(url.toUri())
                .setMediaId(url)
                .setMediaMetadata(metadata)
                .build()

            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        } else {
            exoPlayer.stop()
        }
    }

    public override fun onCleared() {
        super.onCleared()
        playerNotificationManager?.setPlayer(null)
        mediaSession?.release()
        mediaSession = null
        exoPlayer.release()
    }

    fun releasePlayer() {
        playerNotificationManager?.setPlayer(null)
        exoPlayer.pause()
    }

}