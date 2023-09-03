package com.sahar.aflamy.presentation.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.domain.GetConfigurationsUseCase
import com.sahar.aflamy.domain.GetMoviesListUseCase
import com.sahar.aflamy.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase,
    private val configurationsUseCase: GetConfigurationsUseCase
) : BaseViewModel() {

    lateinit var moviesFlow: Flow<PagingData<MoviesListItem>>
        private set

    init {
        getMoviesList()
    }

    fun getMoviesList() {
        try {
            moviesFlow = getMoviesListUseCase.getMoviesList().cachedIn(viewModelScope)
            errorState.value = null
        } catch (e: Exception) {
            errorState.value = e.message
        }
    }

    fun getLogoImagePath(posterPath: String?): String {
        return try {
            configurationsUseCase.getLogoImagePath(posterPath)
        } catch (e: Exception) {
            errorState.value = e.message
            ""
        }
    }

    fun invalidate() {
        try {
            getMoviesListUseCase.invalidate()
        } catch (e: Exception) {
            errorState.value = e.message
        }
    }
}