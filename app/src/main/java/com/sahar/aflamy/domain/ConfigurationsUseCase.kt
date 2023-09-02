package com.sahar.aflamy.domain

import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigurationsUseCase @Inject constructor(
    private val repository: MoviesDataSource,
    private val dispatcher: CoroutineDispatcher
) {

    private var imagesConfigurations: ImagesConfigurations? = null

    init {
        getImagesConfigurations()
    }

    private fun getImagesConfigurations() {
        CoroutineScope(dispatcher).launch {
            try {
                imagesConfigurations = repository.getImagesConfigurations()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun getLogoImagePath(posterPath: String?): String {
        imagesConfigurations?.images?.let {
            return it.secureBaseUrl.plus(
                it.backdropSizes[0]
            ).plus(posterPath)
        }
        return ""
    }

    fun getPosterImagePath(posterPath: String?): String {
        imagesConfigurations?.images?.let {
            val availableSizes = it.posterSizes
            val maxSize = availableSizes.size - 1
            return it.secureBaseUrl.plus(it.posterSizes[maxSize]).plus(posterPath)
        }
        return ""
    }
}