package com.sahar.aflamy.data.repository.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sahar.aflamy.data.model.movieslist.MoviesListItem

/**
 * PagingSource for Movies which retrieve data for required page
 * and keep track of next and previous pages keys
 */
class MoviesPaging(
    private val movieApi: MovieApi,
) : PagingSource<Int, MoviesListItem>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesListItem>): Int? {
        val position = state.anchorPosition ?: 0
        return state.closestPageToPosition(position)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListItem> {
        return try {
            val page = params.key ?: 1
            val response = movieApi.getMovies(page = page)

            LoadResult.Page(
                data = response.moviesList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.moviesList.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}