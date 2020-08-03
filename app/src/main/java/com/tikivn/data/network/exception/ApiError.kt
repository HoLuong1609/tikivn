package com.tikivn.data.network.exception

import com.google.gson.annotations.SerializedName

class ApiError(
    @SerializedName("error")
    var error: String? = null,
    @SerializedName("message")
    var message: String? = null
)