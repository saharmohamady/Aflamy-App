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

class GetMovieDetailsUseCaseTest {

    @MockK
    private lateinit var repository: MoviesDataSource

    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

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
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

        runTest {
            getMovieDetailsUseCase.getMovieDetails("")
            coVerify(exactly = 1) { repository.getMovieDetails("") }
        }
    }

    @Test
    fun `getMovieDetails call repo with same parameter`() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

        runTest {
            getMovieDetailsUseCase.getMovieDetails("testId")
            coVerify(exactly = 1) { repository.getMovieDetails("testId") }
        }
    }

    @Test
    fun `getMovieDetails call repo and return same data`() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(repository)

        runTest {
            assertEquals(getMovieDetailsStub(), getMovieDetailsUseCase.getMovieDetails("testId"))
        }
    }

    private fun getMovieDetailsStub(): MovieDetail {
        return MovieDetail()
    }
}