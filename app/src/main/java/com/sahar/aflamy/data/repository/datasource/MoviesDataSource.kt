package com.sahar.aflamy.data.repository.datasource

import androidx.paging.PagingData
import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    fun getMoviesList(): Flow<PagingData<MoviesListItem>>
    suspend fun getMovieDetails(movieId: String): MovieDetail
    suspend fun getImagesConfigurations(): ImagesConfigurations
    fun invalidate(): Unit?
}