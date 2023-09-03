package com.sahar.aflamy.domain

import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesDataSource
) {

    suspend fun getMovieDetails(movieId: String): MovieDetail? {
        return repository.getMovieDetails(movieId)
    }
}