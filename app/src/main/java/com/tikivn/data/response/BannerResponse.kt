package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

class BannerResponse {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("title")
    var title: String? = ""

    @SerializedName("content")
    var content: String? = ""

    @SerializedName("url")
    var url: String? = ""

    @SerializedName("image_url")
    var imageUrl: String? = ""

    @SerializedName("thumbnail_url")
    var thumbnailUrl: String? = ""

    @SerializedName("mobile_url")
    var mobileUrl: String? = ""

    @SerializedName("ratio")
    var ratio: Double? = 0.0

}