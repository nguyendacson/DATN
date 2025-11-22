package com.example.movieseeme.presentation.viewmodels.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.remote.model.AppState
import com.example.movieseeme.data.remote.model.state.movie.MovieFilter
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.model.enum.HomeTitleFull
import com.example.movieseeme.domain.model.enum.MovieHomeOption
import com.example.movieseeme.domain.model.movie.Category
import com.example.movieseeme.domain.model.movie.MovieDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()
    private val _selectedOption = MutableStateFlow(MovieHomeOption.entries.first())
    val selectedOption = _selectedOption.asStateFlow()

    fun onOptionHomeChange(option: MovieHomeOption) {
        _selectedOption.value = option
    }

    private val _filterHomeState = MutableStateFlow(MovieFilter())
    val filterHomeState: StateFlow<MovieFilter> = _filterHomeState.asStateFlow()

    fun onCategoryChange(value: String) {
        _filterHomeState.value = _filterHomeState.value.copy(category = value)
    }

    fun onTypeChange(value: String) {
        _filterHomeState.value = _filterHomeState.value.copy(type = value)
    }

    private val _listMovieHomeFilter = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieHomeFilter: StateFlow<List<MovieDTO>> = _listMovieHomeFilter

    private val _listMovieAction = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieAction: StateFlow<List<MovieDTO>> = _listMovieAction

    private val _listMovieAnime = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieAnime: StateFlow<List<MovieDTO>> = _listMovieAnime
    private val _listMovieAntique = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieAntique: StateFlow<List<MovieDTO>> = _listMovieAntique
    private val _listMovieFantasy = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieFantasy: StateFlow<List<MovieDTO>> = _listMovieFantasy
    private val _listMovieHistory = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieHistory: StateFlow<List<MovieDTO>> = _listMovieHistory

    private val _listCategory = MutableStateFlow<List<Category>>(emptyList())
    val listCategory: StateFlow<List<Category>> = _listCategory


    fun getMoviesListByCategory(category: String): List<MovieDTO> {
        return when (category) {
            HomeTitleFull.ACTION.slug -> listMovieAction.value
            HomeTitleFull.CARTOON.slug -> listMovieAnime.value
            HomeTitleFull.ANTIQUE.slug -> listMovieAntique.value
            HomeTitleFull.FANTASY.slug -> listMovieFantasy.value
            HomeTitleFull.HISTORY.slug -> listMovieHistory.value
            else -> emptyList()
        }
    }

    init {
        viewModelScope.launch {
            getMoviesAnime()
            getMoviesAction()
            getMoviesAntique()
            getMoviesFantasy()
            getMoviesHistory()
        }

        viewModelScope.launch {
            filterHomeState.collect {
                getMovieHomeFilter()
            }
        }
    }


    private suspend fun getMovieHomeFilter() {
        val filter = filterHomeState.value
        _uiState.value = AppState(isLoading = true)
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = filter.type,
            category = filter.category,
        )) {
            is ApiResult.Success -> {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    message = result.data.message
                )
                _listMovieHomeFilter.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    success = false,
                    message = result.message
                )
            }
        }
    }

    private suspend fun getMoviesAnime() {
        val filter = filterHomeState.value.copy(type = "hoathinh", category = "hoat-hinh")
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = filter.type,
            category = filter.category,
            limit = filter.limit ?: 10
        )) {
            is ApiResult.Success -> {
                _listMovieAnime.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    success = false,
                    message = result.message
                )
            }
        }
    }

    private suspend fun getMoviesAction() {
        val filter = filterHomeState.value.copy(type = "single", category = "hanh-dong")
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = filter.type,
            category = filter.category,
            limit = filter.limit ?: 10
        )) {
            is ApiResult.Success -> {
                _listMovieAction.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    success = false,
                    message = result.message
                )
            }
        }
    }

    private suspend fun getMoviesAntique() {
        val filter = filterHomeState.value.copy(type = "single", category = "co-trang")
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = filter.type,
            category = filter.category,
            limit = filter.limit ?: 10
        )) {
            is ApiResult.Success -> {
                _listMovieAntique.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    success = false,
                    message = result.message
                )
            }
        }
    }

    private suspend fun getMoviesFantasy() {
        val filter = filterHomeState.value.copy(type = "single", category = "vien-tuong")
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = filter.type,
            category = filter.category,
            limit = filter.limit ?: 10
        )) {
            is ApiResult.Success -> {
                _uiState.value = _uiState.value.copy(
                    message = result.data.message
                )
                _listMovieFantasy.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    success = false,
                    message = result.message
                )
            }
        }
    }

    private suspend fun getMoviesHistory() {
        val filter = filterHomeState.value.copy(type = "single", category = "lich-su")
        when (val result = movieRepositoryImpl.getMoviesByFilter(
            type = filter.type,
            category = filter.category,
            limit = filter.limit ?: 10
        )) {
            is ApiResult.Success -> {
                _listMovieHistory.value = result.data.result
            }

            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    success = false,
                    message = result.message
                )
            }
        }
    }

    fun getAllCategory() {
        viewModelScope.launch {
            _uiState.value = AppState(isLoading = true)
            when (val result = movieRepositoryImpl.getAllCategory()) {
                is ApiResult.Success -> {
                    val validCategories = result.data.result
                        .filter { it.name.isNotBlank() }
                        .distinctBy { it.slug }
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        success = true,
                        message = result.data.message,
                    )
                    _listCategory.value = validCategories
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