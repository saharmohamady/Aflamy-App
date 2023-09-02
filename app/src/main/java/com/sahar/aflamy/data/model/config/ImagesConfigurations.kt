package com.sahar.aflamy.data.model.config

import com.google.gson.annotations.SerializedName


data class ImagesConfigurations(

    @SerializedName("images") var images: Images? = Images(),
    @SerializedName("change_keys") var changeKeys: ArrayList<String> = arrayListOf()

)