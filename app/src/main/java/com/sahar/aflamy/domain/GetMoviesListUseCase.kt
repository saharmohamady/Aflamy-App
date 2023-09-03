package com.sahar.aflamy.domain

import androidx.paging.PagingData
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesListUseCase @Inject constructor(
    private val repository: MoviesDataSource
) {

    fun getMoviesList(): Flow<PagingData<MoviesListItem>> {
        return repository.getMoviesList()
    }

    fun invalidate() {
        repository.invalidate()
    }
}