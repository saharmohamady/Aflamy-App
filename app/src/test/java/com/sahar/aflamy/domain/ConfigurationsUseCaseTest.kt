package com.sahar.aflamy.domain

import com.sahar.aflamy.data.model.config.Images
import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ConfigurationsUseCaseTest {

    @MockK
    private lateinit var repository: MoviesDataSource

    lateinit var configurationsUseCase: ConfigurationsUseCase

    @get:Rule
    val testRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getLogoImagePath null path`() {
        configurationsUseCase = ConfigurationsUseCase(repository, UnconfinedTestDispatcher())
        assertEquals("", configurationsUseCase.getLogoImagePath(null))
    }

    @Test
    fun `getLogoImagePath config not loaded yet`() {
        configurationsUseCase = ConfigurationsUseCase(repository, UnconfinedTestDispatcher())
        assertEquals("", configurationsUseCase.getLogoImagePath("/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg"))
    }

    @Test
    fun `getLogoImagePath config loaded`() {
        coEvery { repository.getImagesConfigurations() } returns getStubImagesConfigurations()
        configurationsUseCase = ConfigurationsUseCase(repository, UnconfinedTestDispatcher())
        assertEquals(
            "https://image.tmdb.org/t/p/w300/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg",
            configurationsUseCase.getLogoImagePath("/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg")
        )
    }

    @Test
    fun `getPosterImagePath config not loaded yet`() {
        configurationsUseCase = ConfigurationsUseCase(repository, UnconfinedTestDispatcher())
        assertEquals(
            "",
            configurationsUseCase.getPosterImagePath("/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg")
        )
    }

    @Test
    fun getPosterImagePathWhenDataLoaded() {
        coEvery { repository.getImagesConfigurations() } returns getStubImagesConfigurations()
        configurationsUseCase = ConfigurationsUseCase(repository, UnconfinedTestDispatcher())
        assertEquals(
            "https://image.tmdb.org/t/p/original/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg",
            configurationsUseCase.getPosterImagePath("/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg")
        )

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
}