package com.example.movieseeme.presentation.viewmodels.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.remote.model.state.profile.MovieProfileFilter
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.model.enum.ProfileTitleFull
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.domain.model.movie.MovieWatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InteractionViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) : ViewModel() {

    private val _uiStateInteraction = MutableStateFlow(AppState())
    val uiStateInteraction: StateFlow<AppState> = _uiStateInteraction.asStateFlow()


    private val _uiStateAction = MutableStateFlow(AppState())
    val uiStateAction: StateFlow<AppState> = _uiStateAction.asStateFlow()

    fun clearMessage() {
        _uiStateInteraction.value = uiStateInteraction.value.copy(message = "")
        _uiStateAction.value = _uiStateAction.value.copy(message = "")
    }


    private val _selectedMovie = MutableStateFlow(MovieProfileFilter())
    val selectedMovie = _selectedMovie.asStateFlow()

    fun onItemClick(
        id: String,
        name: String,
        isWatching: Boolean,
        type: String,
        dataMovieId: String
    ) {
        _selectedMovie.value = MovieProfileFilter(
            dataMovieId = dataMovieId,
            id = id,
            name = name,
            isWatching = isWatching,
            type = type
        )
    }

    fun closeSheet() {
        _selectedMovie.value = MovieProfileFilter()
    }

    private val _listMovieWatching = MutableStateFlow<List<MovieWatching>>(emptyList())
    val listMovieWatching = _listMovieWatching.asStateFlow()

    private val _listMovieTrailer = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieTrailer = _listMovieTrailer.asStateFlow()

    private val _listMovieListForYou = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieListForYou = _listMovieListForYou.asStateFlow()

    private val _listMovieLikes = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieLikes = _listMovieLikes.asStateFlow()

    private val _listMovieMyList = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieMyList = _listMovieMyList.asStateFlow()


    // ===================== INIT LOAD =====================


    fun getMoviesListByUser(slug: String): List<MovieDTO> {
        return when (slug) {
            ProfileTitleFull.LIKE.slug -> listMovieLikes.value
            ProfileTitleFull.TRAILER.slug -> listMovieTrailer.value
            ProfileTitleFull.MY_LIST.slug -> listMovieMyList.value
            ProfileTitleFull.LIST_FOR_YOU.slug -> listMovieListForYou.value
            else -> emptyList()
        }
    }

    init {
        viewModelScope.launch {
            getMoviesForYou()
            getMoviesTrailer()
            getMoviesLike()
            getMoviesMyList()
            getMoviesWatching()
        }
    }


    // ===================== GET MOVIES =====================
    fun getMoviesForYou() {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getMoviesForYou()) {
                is ApiResult.Success -> {
                    _uiStateInteraction.value = AppState(success = true)
                    _listMovieListForYou.value = result.data.result
                }

                is ApiResult.Error -> {
                    _uiStateInteraction.value =
                        AppState(success = false, message = result.message)
                }
            }
        }
    }

    fun getMoviesWatching() {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getMoviesWatching()) {
                is ApiResult.Success -> {
                    _listMovieWatching.value = result.data.result
                }

                is ApiResult.Error -> {
                    _uiStateInteraction.value =
                        AppState(success = false, message = result.message)
                }
            }
        }
    }

    fun getMoviesTrailer() {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getMoviesTrailers()) {
                is ApiResult.Success -> {
                    _listMovieTrailer.value = result.data.result
                }

                is ApiResult.Error -> {
                    _uiStateInteraction.value =
                        AppState(success = false, message = result.message)
                }
            }
        }
    }

    fun getMoviesLike() {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getMoviesLike()) {
                is ApiResult.Success -> {
                    _listMovieLikes.value = result.data.result
                }

                is ApiResult.Error -> {
                    _uiStateInteraction.value =
                        AppState(success = false, message = result.message)
                }
            }
        }
    }

    fun getMoviesMyList() {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getMoviesMyList()) {
                is ApiResult.Success -> {
                    _listMovieMyList.value = result.data.result
                }

                is ApiResult.Error -> {
                    _uiStateInteraction.value =
                        AppState(success = false, message = result.message)
                }
            }
        }
    }


    // ===================== ACTIONS (ADD / DELETE) =====================

    fun postMovieToMyList(movieId: String) {
        viewModelScope.launch {
            _uiStateAction.value = AppState(isLoading = true)

            when (movieRepositoryImpl.postMovieToMyList(movieId)) {
                is ApiResult.Success -> {

                    _uiStateAction.value =
                        AppState(success = true, message = "Đã thêm vào danh sách Yêu Thích")
                    getMoviesMyList()   // 🔥 reload ngay
                }

                is ApiResult.Error -> {
                    _uiStateAction.value = AppState(success = false, message = "Đã tồn tại")
                }
            }
        }
    }

    fun postMovieToLike(movieId: String) {
        viewModelScope.launch {
            _uiStateAction.value = AppState(isLoading = true)

            when (movieRepositoryImpl.postMovieToLike(movieId)) {
                is ApiResult.Success -> {

                    _uiStateAction.value =
                        AppState(success = true, message = "Đã thêm vào danh sách Like")
                    getMoviesLike()
                }

                is ApiResult.Error -> {
                    _uiStateAction.value = AppState(success = false, message = "Đã tồn tại")
                }
            }
        }
    }

    fun onDelete() {
        val filter = selectedMovie.value
        val id = filter.id
        val dataMovieId = filter.dataMovieId

        if (id.isBlank()) return

        when {
            filter.isWatching -> {
                deleteMovieToWatchList(dataMovieId)

            }

            filter.type == ProfileTitleFull.LIKE.slug -> deleteMovieToLike(id)
            filter.type == ProfileTitleFull.TRAILER.slug -> deleteMovieToTrailer(id)
            filter.type == ProfileTitleFull.MY_LIST.slug -> deleteMovieFromMyList(id)
        }

        closeSheet()
    }


    private fun deleteMovieToLike(id: String) {
        viewModelScope.launch {
            when (movieRepositoryImpl.deleteMovieToLike(id)) {
                is ApiResult.Success -> {
                    _listMovieLikes.value = _listMovieLikes.value
                        .filter { it.id != id }
                    getMoviesLike()

                    _uiStateAction.value = AppState(success = true, message = "Đã xóa khỏi Like")
                }

                is ApiResult.Error -> {
                    _uiStateAction.value = AppState(success = false, message = "Xóa thất bại")
                }
            }
        }
    }

    private fun deleteMovieFromMyList(id: String) {
        viewModelScope.launch {
            when (movieRepositoryImpl.deleteMovieToMyList(id)) {
                is ApiResult.Success -> {
                    _listMovieMyList.value = _listMovieMyList.value
                        .filter { it.id != id }
                    getMoviesMyList()
                    _uiStateAction.value =
                        AppState(success = true, message = "Đã xóa khỏi danh sách Yêu Thích")
                }

                is ApiResult.Error -> {
                    _uiStateAction.value = AppState(success = false, message = "Xóa thất bại")
                }
            }
        }
    }

    private fun deleteMovieToWatchList(dataMovieId: String) {
        viewModelScope.launch {
            when (movieRepositoryImpl.deleteWatching(dataMovieId)) {
                is ApiResult.Success -> {
                    _listMovieWatching.value = _listMovieWatching.value
                        .filter { it.dataMovieId != dataMovieId }
                    getMoviesWatching()
                    _uiStateAction.value =
                        AppState(success = true, message = "Đã xóa khỏi Đã Xem")
                }

                is ApiResult.Error -> {
                    _uiStateAction.value = AppState(success = false, message = "Xóa thất bại")
                }
            }
        }
    }

    private fun deleteMovieToTrailer(id: String) {
        viewModelScope.launch {
            when (movieRepositoryImpl.deleteMovieToTrailers(id)) {
                is ApiResult.Success -> {
                    _listMovieTrailer.value = _listMovieTrailer.value
                        .filter { it.id != id }
                    getMoviesTrailer()
                    _uiStateAction.value =
                        AppState(success = true, message = "Đã xóa khỏi Trailer")
                }

                is ApiResult.Error -> {
                    _uiStateAction.value = AppState(success = false, message = "Xóa thất bại")
                }
            }
        }
    }
}