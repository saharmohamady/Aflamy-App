package com.sahar.aflamy.domain

import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailsUseCaseTest {

    @MockK
    private lateinit var repository: MoviesDataSource

    lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @get:Rule
    val testRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { repository.getMovieDetails(any()) } returns getMovieDetailsStub()
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getMovieDetails call repo exactly once`() {
        movieDetailsUseCase = MovieDetailsUseCase(repository)

        runTest {
            movieDetailsUseCase.getMovieDetails("")
            coVerify(exactly = 1) { repository.getMovieDetails("") }
        }
    }

    @Test
    fun `getMovieDetails call repo with same parameter`() {
        movieDetailsUseCase = MovieDetailsUseCase(repository)

        runTest {
            movieDetailsUseCase.getMovieDetails("testId")
            coVerify(exactly = 1) { repository.getMovieDetails("testId") }
        }
    }

    @Test
    fun `getMovieDetails call repo and return same data`() {
        movieDetailsUseCase = MovieDetailsUseCase(repository)

        runTest {
            assertEquals(getMovieDetailsStub(), movieDetailsUseCase.getMovieDetails("testId"))
        }
    }

    private fun getMovieDetailsStub(): MovieDetail {
        return MovieDetail()
    }
}