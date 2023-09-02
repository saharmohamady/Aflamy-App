package com.sahar.aflamy.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.repository.datasource.MoviesDataSource
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val movieApi: MovieApi) : MoviesDataSource {

    @VisibleForTesting
    @androidx.annotation.VisibleForTesting
    var moviesPaging: MoviesPaging? = null
        get() {
            if (field == null || field?.invalid == true)
                moviesPaging = MoviesPaging(movieApi)
            return field
        }
        private set

    override fun getMoviesList() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            moviesPaging!!
        }
    ).flow

    override fun invalidate() = moviesPaging?.invalidate()

    override suspend fun getMovieDetails(movieId: String): MovieDetail {
        return movieApi.getMovieDetail(movieId)
    }

    override suspend fun getImagesConfigurations(): ImagesConfigurations {
        return movieApi.getImagesConfigurations()
    }
}