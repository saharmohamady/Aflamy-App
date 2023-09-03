package com.sahar.aflamy.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.domain.GetConfigurationsUseCase
import com.sahar.aflamy.domain.GetMovieDetailsUseCase
import com.sahar.aflamy.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val configurationsUseCase: GetConfigurationsUseCase
) : BaseViewModel() {

    private val _movieDetails: MutableState<MovieDetail?> = mutableStateOf(null)
    val movieDetail: State<MovieDetail?> = _movieDetails

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                _movieDetails.value = getMovieDetailsUseCase.getMovieDetails(movieId)
                errorState.value = null
            } catch (e: Exception) {
                onError(e.message)
            }
        }
    }

    private fun onError(message: String?) {
        errorState.value = message
    }

    fun getLogoImagePath(posterPath: String?): String {
        return configurationsUseCase.getPosterImagePath(posterPath)
    }
}
