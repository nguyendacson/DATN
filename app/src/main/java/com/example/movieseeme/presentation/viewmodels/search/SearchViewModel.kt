package com.example.movieseeme.presentation.viewmodels.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeme.data.remote.model.ApiResult
import com.example.movieseeme.data.repository.MovieRepositoryImpl
import com.example.movieseeme.domain.model.movie.MovieDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepositoryImpl: MovieRepositoryImpl
) : ViewModel() {

    private val _keySearch = MutableStateFlow(String())
    val keySearch: StateFlow<String> = _keySearch.asStateFlow()

    fun onKeySearchChange(value: String) {
        _keySearch.value = value
    }

    fun clearSearchResult() {
        _listMovieSearch.value = emptyList()
    }

    private val _listMovieSearch = MutableStateFlow<List<MovieDTO>>(emptyList())
    val listMovieSearch: StateFlow<List<MovieDTO>> = _listMovieSearch

    fun getMoviesSearch() {
        viewModelScope.launch {
            val key = keySearch.value
            when (val result = movieRepositoryImpl.getMoviesSearch(
                key = key
            )) {
                is ApiResult.Success -> {
                    _listMovieSearch.value = result.data.result
                }

                is ApiResult.Error -> {
//                    Log.e("SearchViewModel", "ERROR")
                }
            }
        }

    }
}