package com.sahar.aflamy.repository

import com.sahar.aflamy.data.model.config.Images
import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.model.movieslist.MoviesListResponse
import com.sahar.aflamy.data.repository.remote.MovieApi
import com.sahar.aflamy.data.repository.remote.MoviesRepository
import com.sahar.aflamy.domain.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesRepositoryTest {

    @MockK
    private lateinit var movieApi: MovieApi

    @get:Rule
    val testRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { movieApi.getMovieDetail("1") } returns MovieDetail(id = 1)
        coEvery { movieApi.getMovieDetail("123") } returns MovieDetail(id = 123)
        coEvery { movieApi.getImagesConfigurations() } returns getStubImagesConfigurations()
        coEvery { movieApi.getMovies(any(), any()) } returns getMoviesResponse()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getMovieDetails verify called`() {
        runTest {
            val repository = MoviesRepository(movieApi)
            repository.getMovieDetails("1")
            coVerify(exactly = 1) { movieApi.getMovieDetail("1") }
        }
    }

    @Test
    fun `getMovieDetails verify return val`() {
        runTest {
            val repository = MoviesRepository(movieApi)
            repository.getMovieDetails("1")
            assertEquals(MovieDetail(id = 1), movieApi.getMovieDetail("1"))
        }
    }

    @Test
    fun `getMovieDetails verify parameter passed`() {
        runTest {
            val repository = MoviesRepository(movieApi)
            repository.getMovieDetails("123")
            assertEquals(MovieDetail(id = 123), movieApi.getMovieDetail("123"))
        }
    }

    @Test
    fun getImagesConfigurations() {
        runTest {
            val repository = MoviesRepository(movieApi)
            repository.getImagesConfigurations()
            coVerify(exactly = 1) { movieApi.getImagesConfigurations() }
        }
    }

    @Test
    fun `getImagesConfigurations assert response`() {
        runTest {
            val repository = MoviesRepository(movieApi)
            assertEquals(getStubImagesConfigurations(), repository.getImagesConfigurations())
        }
    }

    private fun getStubImagesConfigurations(): ImagesConfigurations {
        val config = ImagesConfigurations()
        config.images = Images(
            secureBaseUrl = "https://image.tmdb.org/t/p/",
            backdropSizes = arrayListOf("w300", "w780", "w1280", "original"),
            posterSizes = arrayListOf("w92", "w154", "w185", "w342", "w500", "w780", "original")
        )
        return config
    }

    private fun getMoviesResponse(): MoviesListResponse {
        return MoviesListResponse(1, arrayListOf())
    }

}