package com.sahar.aflamy.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import app.cash.turbine.test
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.data.model.movieslist.MoviesListResponse
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test

class MoviesPagingTest {

    @MockK
    private lateinit var movieApi: MovieApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { movieApi.getMovies(1, any()) } returns getMoviesList(1)
        coEvery { movieApi.getMovies(2, any()) } returns getMoviesList(2)

    }

    private fun getMoviesList(page: Int): MoviesListResponse {
        return MoviesListResponse(page = page).apply {
            moviesList = arrayListOf(
                MoviesListItem(id = 1 * page),
                MoviesListItem(id = 2 * page),
                MoviesListItem(id = 3 * page)
            )
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun loadInitial() = runTest {
        val moviesPaging = MoviesPaging(movieApi)

        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                moviesPaging
            }
        )

        val job = launch {
            pager.flow.test {
                val result = this.awaitItem()
                result.map { paging ->
                    assertArrayEquals(paging.toArray(), getMoviesList(1).moviesList.toTypedArray())
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        moviesPaging.load(PagingSource.LoadParams.Refresh(1, 20, false))

        job.join()
        job.cancel()
    }

    @Test
    fun loadRefresh() = runTest {
        val moviesPaging = MoviesPaging(movieApi)

        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                moviesPaging
            }
        )

        val job = launch {
            pager.flow.test {
                val result = this.awaitItem()
                result.map { paging ->
                    assertArrayEquals(paging.toArray(), getMoviesList(2).moviesList.toTypedArray())

                }
                cancelAndConsumeRemainingEvents()
            }
        }
        moviesPaging.load(PagingSource.LoadParams.Refresh(2, 20, false))

        job.join()
        job.cancel()
    }
}