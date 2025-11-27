package com.example.movieseeme.presentation.viewmodels.movie.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.enum_class.detail.MovieDetailTab
import com.example.movieseeme.domain.model.movie.MovieDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) : ViewModel() {
    private val _detailState = MutableStateFlow(AppState())
    val detailState: StateFlow<AppState> = _detailState.asStateFlow()

    private val _movieDetail = MutableStateFlow<MovieDTO?>(null)
    val movieDetail: StateFlow<MovieDTO?> = _movieDetail.asStateFlow()

    private val _movieDetailSame = MutableStateFlow<List<MovieDTO>>(emptyList())
    val movieDetailSame: StateFlow<List<MovieDTO>> = _movieDetailSame.asStateFlow()

    private val _actorsDetail = MutableStateFlow<List<String>>(emptyList())
    val actorsDetail: StateFlow<List<String>> = _actorsDetail.asStateFlow()

    var isExpanded by mutableStateOf(false)
        private set

    private val _putMovieID = MutableStateFlow("")
    val putMovieID: StateFlow<String> = _putMovieID.asStateFlow()


    fun toggleExpand() {
        isExpanded = !isExpanded
    }

    fun clearMessage() {
        _detailState.value = _detailState.value.copy(message = "", success = false)
    }

    private val _selectedContent = MutableStateFlow(MovieDetailTab.entries.first())
    val selectedContent = _selectedContent.asStateFlow()

    private val _contentForCurrentTab = MutableStateFlow("Đang cập nhật")
    val contentForCurrentTab = _contentForCurrentTab.asStateFlow()

    val contentDetail: String
        get() = movieDetail.value?.content ?: "Đang cập nhật"

    val directorDetail: String
        get() = movieDetail.value?.directors
            ?.map { it.name }
            ?.filter { it.isNotBlank() }
            ?.joinToString(", ")
            ?: "Đang cập nhật"

    val actors: String
        get() = actorsDetail.value
            .filter { it.isNotBlank() }
            .joinToString(", ")


    fun updateContent() {
        _contentForCurrentTab.value = when (selectedContent.value) {
            MovieDetailTab.CONTENT -> contentDetail
            MovieDetailTab.DIRECTOR -> directorDetail
            MovieDetailTab.ACTORS -> actors
        }
    }

    fun onOptionContent(option: MovieDetailTab) {
        _selectedContent.value = option
        updateContent()
    }

    fun getMovieById(movieId: String) {
        viewModelScope.launch {
            _detailState.value = AppState(isLoading = true)
            when (val result = movieRepositoryImpl.getMovieById(movieId)) {
                is ApiResult.Success -> {
                    _movieDetail.value = result.data.result
                    updateContent()
                    getActorById(movieId)
                    getMovieSame()
                    _putMovieID.value = movieId
                }

                is ApiResult.Error -> {
                    //
                }
            }
        }
    }

    fun getActorById(movieId: String) {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getActorById(movieId)) {
                is ApiResult.Success -> {
                    _actorsDetail.value = result.data.result
                    updateContent()
                }

                is ApiResult.Error -> {
                    //
                }
            }
        }
    }

    fun getMovieSame() {
        viewModelScope.launch {
            when (val result = movieRepositoryImpl.getMoviesByFilter(
                type = movieDetail.value?.type ?: "series",
                category = movieDetail.value?.categories
                    ?.firstOrNull()
                    ?.slug
            )) {
                is ApiResult.Success -> {
                    _movieDetailSame.value = result.data.result
                }

                is ApiResult.Error -> {
                    //
                }
            }
        }
    }
}