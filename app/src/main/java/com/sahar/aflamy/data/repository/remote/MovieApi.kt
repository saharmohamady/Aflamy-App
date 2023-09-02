package com.sahar.aflamy.data.repository.remote

import com.sahar.aflamy.BuildConfig
import com.sahar.aflamy.data.model.config.ImagesConfigurations
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.data.model.movieslist.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int? = null,
        @Query("api_key") api_key: String? = BuildConfig.API_KEY
    ): MoviesListResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") api_key: String? = BuildConfig.API_KEY
    ): MovieDetail

    @GET("https://api.themoviedb.org/3/configuration")
    suspend fun getImagesConfigurations(
        @Query("api_key") api_key: String? = BuildConfig.API_KEY
    ): ImagesConfigurations
}