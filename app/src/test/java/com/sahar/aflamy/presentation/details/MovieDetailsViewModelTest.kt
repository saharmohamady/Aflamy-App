package com.sahar.aflamy.presentation.details

import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.domain.ConfigurationsUseCase
import com.sahar.aflamy.domain.CoroutineTestRule
import com.sahar.aflamy.domain.MovieDetailsUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {

    @MockK
    lateinit var configurationsUseCase: ConfigurationsUseCase

    @MockK
    lateinit var movieDetailsUseCase: MovieDetailsUseCase

    @get:Rule
    val testRule = CoroutineTestRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getMovieDetail assert no error`() = runBlocking {
        coEvery { movieDetailsUseCase.getMovieDetails(any()) } returns MovieDetail()
        val viewModel = MovieDetailsViewModel(
            movieDetailsUseCase = movieDetailsUseCase,
            configurationsUseCase = configurationsUseCase
        )
        viewModel.getMovieDetails("test")
        assertNotNull(viewModel.movieDetail.value)
    }

    @Test
    fun `getMovieDetail assert returned value`() = runBlocking {
        val movie = MovieDetail()
        coEvery { movieDetailsUseCase.getMovieDetails(any()) } returns movie
        val viewModel = MovieDetailsViewModel(
            movieDetailsUseCase = movieDetailsUseCase,
            configurationsUseCase = configurationsUseCase
        )
        viewModel.getMovieDetails("test")
        assertEquals(movie, viewModel.movieDetail.value)
    }

    @Test
    fun `getMovieDetail assert No error`() = runBlocking {
        val movie = MovieDetail()
        coEvery { movieDetailsUseCase.getMovieDetails(any()) } returns movie
        val viewModel = MovieDetailsViewModel(
            movieDetailsUseCase = movieDetailsUseCase,
            configurationsUseCase = configurationsUseCase
        )
        viewModel.getMovieDetails("test")
        assertNull(viewModel.errorState.value)
    }

    @Test
    fun `getLogoImagePath verify correct use case called`() {
        coEvery { configurationsUseCase.getPosterImagePath(any()) } returns "testUrl"
        runBlocking {
            val viewModel = MovieDetailsViewModel(
                movieDetailsUseCase = movieDetailsUseCase,
                configurationsUseCase = configurationsUseCase
            )
            viewModel.getLogoImagePath("testUrl")
            coVerify { configurationsUseCase.getPosterImagePath("testUrl") }
        }
    }

}