package com.sahar.aflamy.data.model.movieslist

import com.google.gson.annotations.SerializedName
import com.sahar.aflamy.data.model.GenericNetworkResponse
import java.util.*


data class MoviesListResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var moviesList: ArrayList<MoviesListItem> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
) : GenericNetworkResponse()