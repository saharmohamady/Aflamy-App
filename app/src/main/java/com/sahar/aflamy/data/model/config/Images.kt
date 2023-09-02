package com.sahar.aflamy.data.model.config

import com.google.gson.annotations.SerializedName
import java.util.*


data class Images(

    @SerializedName("base_url") var baseUrl: String? = null,
    @SerializedName("secure_base_url") var secureBaseUrl: String? = null,
    @SerializedName("backdrop_sizes") var backdropSizes: ArrayList<String> = arrayListOf(),
    @SerializedName("logo_sizes") var logoSizes: ArrayList<String> = arrayListOf(),
    @SerializedName("poster_sizes") var posterSizes: ArrayList<String> = arrayListOf(),
    @SerializedName("profile_sizes") var profileSizes: ArrayList<String> = arrayListOf(),
    @SerializedName("still_sizes") var stillSizes: ArrayList<String> = arrayListOf()

)