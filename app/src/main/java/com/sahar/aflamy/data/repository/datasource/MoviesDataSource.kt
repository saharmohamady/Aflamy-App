package com.sahar.aflamy.data.repository.datasource

import androidx.paging.PagingData
import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import kotlinx.coroutines.flow.Flow
/**
 * represent all function that app needs to function properly so any repo should provide this
 */
interface MoviesDataSource {

    /**
     * Retrieve list of movies as paged data to allow load more function on UI
     */
    fun getMoviesList(): Flow<PagingData<MoviesListItem>>

    /**
     * Retrieve full details of a movie
     * [movieId] is the id of the required movie to retrieve its full details
     */
    suspend fun getMovieDetails(movieId: String): MovieDetail

    /**
     * list of configurations required to build full image url
     */
    suspend fun getImagesConfigurations(): ImagesConfigurations

    /**
     * Refresh data loaded
     */
    fun invalidate(): Unit?
}