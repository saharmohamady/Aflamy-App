package com.sahar.aflamy.data.model

import com.google.gson.annotations.SerializedName

open class GenericNetworkResponse(
    @SerializedName("status_code") var statusCode: Int? = null,
    @SerializedName("status_message") var statusMessage: String? = null,
    @SerializedName("success") var success: Boolean? = null
)