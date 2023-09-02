package com.sahar.aflamy.presentation.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.domain.ConfigurationsUseCase
import com.sahar.aflamy.domain.MoviesListUseCase
import com.sahar.aflamy.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesListUseCase: MoviesListUseCase,
    private val configurationsUseCase: ConfigurationsUseCase
) : BaseViewModel() {

    lateinit var moviesFlow: Flow<PagingData<MoviesListItem>>
        private set

    init {
        getMoviesList()
    }

    fun getMoviesList() {
        try {
            moviesFlow = moviesListUseCase.getMoviesList().cachedIn(viewModelScope)
            _errorState.value = null
        } catch (e: Exception) {
            _errorState.value = e.message
        }
    }

    fun getLogoImagePath(posterPath: String?): String {
        return try {
            configurationsUseCase.getLogoImagePath(posterPath)
        } catch (e: Exception) {
            _errorState.value = e.message
            ""
        }
    }

    fun invalidate() {
        try {
            moviesListUseCase.invalidate()
        } catch (e: Exception) {
            _errorState.value = e.message
        }
    }
}