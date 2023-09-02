package com.sahar.aflamy.presentation.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.data.model.movieslist.MoviesListResponse
import com.sahar.aflamy.data.repository.remote.MovieApi
import com.sahar.aflamy.data.repository.remote.MoviesPaging
import com.sahar.aflamy.domain.ConfigurationsUseCase
import com.sahar.aflamy.domain.CoroutineTestRule
import com.sahar.aflamy.domain.MoviesListUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesListViewModelTest {

    @MockK
    lateinit var configurationsUseCase: ConfigurationsUseCase

    @MockK
    lateinit var moviesListUseCase: MoviesListUseCase

    @MockK
    private lateinit var movieApi: MovieApi

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
    fun getMoviesList() {
        coEvery { moviesListUseCase.getMoviesList() } returns getMoviesListStub()
        runBlocking {
            val viewModel = MoviesListViewModel(
                moviesListUseCase = moviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            val job = launch {
                viewModel.moviesFlow.test {
                    val result = this.awaitItem()
                    result.map { paging ->
                        assertEquals(paging, getMoviesResponse())
                    }
                    cancelAndConsumeRemainingEvents()
                }
            }
            viewModel.getMoviesList()
            job.join()
            job.cancel()
        }
    }

    @Test
    fun `when viewModel initialized verify repo called`() {
        coEvery { moviesListUseCase.getMoviesList() } returns getMoviesListStub()
        runBlocking {
            MoviesListViewModel(
                moviesListUseCase = moviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            coVerify(exactly = 1) { moviesListUseCase.getMoviesList() }
        }
    }

    @Test
    fun `when viewModel initialized assert null error`() {
        coEvery { moviesListUseCase.getMoviesList() } returns getMoviesListStub()
        runBlocking {
            val viewModel = MoviesListViewModel(
                moviesListUseCase = moviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            assertNull(viewModel.errorState.value)
        }
    }

    @Test
    fun `getLogoImagePath verify correct use case called`() {
        coEvery { configurationsUseCase.getLogoImagePath(any()) } returns "testUrl"
        runBlocking {
            val viewModel = MoviesListViewModel(
                moviesListUseCase = moviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            viewModel.getLogoImagePath("testUrl")
            coVerify { configurationsUseCase.getLogoImagePath("testUrl") }
        }
    }

    @Test
    fun `invalidate verify correct use case called`() {
        coEvery { moviesListUseCase.invalidate() }
        runBlocking {
            val viewModel = MoviesListViewModel(
                moviesListUseCase = moviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            viewModel.invalidate()
            coVerify { moviesListUseCase.invalidate() }
        }
    }

    private fun getMoviesResponse(): MoviesListResponse {
        return MoviesListResponse(1, arrayListOf())
    }

    private fun getMoviesListStub(): Flow<PagingData<MoviesListItem>> {
        val flow: Flow<PagingData<MoviesListItem>> = Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                MoviesPaging(movieApi = movieApi)
            }
        ).flow
        return flow
    }
}