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
import com.sahar.aflamy.domain.CoroutineTestRule
import com.sahar.aflamy.domain.GetConfigurationsUseCase
import com.sahar.aflamy.domain.GetMoviesListUseCase
import io.mockk.*
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
    lateinit var configurationsUseCase: GetConfigurationsUseCase

    @MockK
    lateinit var getMoviesListUseCase: GetMoviesListUseCase

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
        coEvery { getMoviesListUseCase.getMoviesList() } returns getMoviesListStub()
        runBlocking {
            val viewModel = MoviesListViewModel(
                getMoviesListUseCase = getMoviesListUseCase,
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
        coEvery { getMoviesListUseCase.getMoviesList() } returns getMoviesListStub()
        runBlocking {
            MoviesListViewModel(
                getMoviesListUseCase = getMoviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            coVerify(exactly = 1) { getMoviesListUseCase.getMoviesList() }
        }
    }

    @Test
    fun `when viewModel initialized assert null error`() {
        coEvery { getMoviesListUseCase.getMoviesList() } returns getMoviesListStub()
        runBlocking {
            val viewModel = MoviesListViewModel(
                getMoviesListUseCase = getMoviesListUseCase,
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
                getMoviesListUseCase = getMoviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            viewModel.getLogoImagePath("testUrl")
            coVerify { configurationsUseCase.getLogoImagePath("testUrl") }
        }
    }

    @Test
    fun `invalidate verify correct use case called`() {
        every { getMoviesListUseCase.invalidate() } returns Unit
        every { configurationsUseCase.invalidate() } returns Unit

        runBlocking {
            val viewModel = MoviesListViewModel(
                getMoviesListUseCase = getMoviesListUseCase,
                configurationsUseCase = configurationsUseCase
            )
            viewModel.invalidate()
            coVerify { getMoviesListUseCase.invalidate() }
            coVerify { configurationsUseCase.invalidate() }
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