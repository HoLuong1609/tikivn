package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {
    @SerializedName("data")
    var data: T? = null
}