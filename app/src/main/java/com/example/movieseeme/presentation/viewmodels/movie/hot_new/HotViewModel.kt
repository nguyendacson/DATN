package com.example.movieseeme.presentation.viewmodels.movie.hot_new

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.remote.model.state.movie.MovieFilter
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.enum_class.MovieHotOption
import com.example.movieseeme.domain.model.movie.MovieDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) : ViewModel() {
    private val _selectedHotOption = MutableStateFlow(MovieHotOption.entries.first())
    val selectedHotOption = _selectedHotOption.asStateFlow()

    fun onOptionHotChange(option: MovieHotOption) {
        _selectedHotOption.value = option
    }

    private val _filterHotState = MutableStateFlow(MovieFilter())
    val filterHotState: StateFlow<MovieFilter> = _filterHotState.asStateFlow()

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    private val _listMovieHotFilter = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieHotFilter: StateFlow<List<MovieDTO>> = _listMovieHotFilter

    private val _listMovieListForYou = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieListForYou: StateFlow<List<MovieDTO>> = _listMovieListForYou

    fun onStatusChange(value: String) {
        _filterHotState.value = _filterHotState.value.copy(status = value)
    }

    init {
        viewModelScope.launch {
            selectedHotOption.collect { selected ->
                _uiState.value = _uiState.value.copy(isLoading = true)

                when (selected.key) {
                    1 -> {
                        onStatusChange("ongoing")
                        getMoviesHotFilter()
                    }

                    2 -> {
                        getMoviesForYouHot()
                    }

                    3 -> {
                        onStatusChange("completed")
                        getMoviesHotFilter()
                    }
                }
            }
        }
    }

    suspend fun getMoviesHotFilter() {
        _uiState.value = AppState(isLoading = true)
        val filter = filterHotState.value
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = "series",
            limit = filter.limit ?: 10,
            sortField = filter.sortField.toString(),
            year = filter.year,
            status = filter.status
        )) {
            is ApiResult.Success -> {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    success = true,
                    message = result.data.message,
                )
                _listMovieHotFilter.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    success = false,
                    message = result.message
                )
            }
        }
    }

    fun getMoviesForYouHot() {
        viewModelScope.launch {
            _uiState.value = AppState(isLoading = true)
            when (val result = movieRepositoryImpl.getMoviesForYou()) {
                is ApiResult.Success -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = true,
                        message = result.data.message,
                    )
                    _listMovieHotFilter.value = result.data.result
                }

                is ApiResult.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = false,
                        message = result.message
                    )
                }
            }
        }
    }

}