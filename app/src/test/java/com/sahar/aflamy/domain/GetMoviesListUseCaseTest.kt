package com.sahar.aflamy.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import com.sahar.aflamy.data.repository.remote.MovieApi
import com.sahar.aflamy.data.repository.remote.MoviesPaging
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetMoviesListUseCaseTest {

    @MockK
    private lateinit var repository: MoviesDataSource

    @MockK
    private lateinit var movieApi: MovieApi

    lateinit var getMoviesListUseCase: GetMoviesListUseCase

    @get:Rule
    val testRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { repository.getMoviesList() } returns getMoviesListStub()
        every { repository.invalidate() } returns Unit
    }


    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `moviesListUseCase call repo exactly once`() {
        getMoviesListUseCase = GetMoviesListUseCase(repository)

        runTest {
            getMoviesListUseCase.getMoviesList()
            coVerify(exactly = 1) { repository.getMoviesList() }
        }
    }

    @Test
    fun `invalidate call repo exactly once`() {
        getMoviesListUseCase = GetMoviesListUseCase(repository)

        runTest {
            getMoviesListUseCase.invalidate()
            coVerify(exactly = 1) { repository.invalidate() }
        }
    }

//    @Test
//    fun `moviesListUseCase return same results`() {
//        moviesListUseCase = MoviesListUseCase(repository)
//
//        runTest(UnconfinedTestDispatcher()) {
//            assertEquals(getMoviesListStub().count(), moviesListUseCase.getMoviesList().count())
//        }
//
//    }

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