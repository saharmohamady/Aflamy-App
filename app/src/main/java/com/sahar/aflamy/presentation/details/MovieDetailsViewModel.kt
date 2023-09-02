package com.sahar.aflamy.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.domain.ConfigurationsUseCase
import com.sahar.aflamy.domain.MovieDetailsUseCase
import com.sahar.aflamy.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val configurationsUseCase: ConfigurationsUseCase
) : BaseViewModel() {

    private val _movieDetails: MutableState<MovieDetail?> = mutableStateOf(null)
    val movieDetail: State<MovieDetail?> = _movieDetails

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                _movieDetails.value = movieDetailsUseCase.getMovieDetails(movieId)
                _errorState.value = null
            } catch (e: Exception) {
                onError(e.message)
            }
        }
    }

    private fun onError(message: String?) {
        _errorState.value = message
    }

    fun getLogoImagePath(posterPath: String?): String {
        return configurationsUseCase.getPosterImagePath(posterPath)
    }
}
