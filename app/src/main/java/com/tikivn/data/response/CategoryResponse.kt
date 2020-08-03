package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

class CategoryResponse {
    @SerializedName("title")
    var title: String? = ""

    @SerializedName("content")
    var content: String? = ""

    @SerializedName("url")
    var url: String? = ""

    @SerializedName("authentication")
    var authentication: Boolean? = false

    @SerializedName("web_url")
    var webUrl: String? = ""

    @SerializedName("image_url")
    var imageUrl: String? = ""

}