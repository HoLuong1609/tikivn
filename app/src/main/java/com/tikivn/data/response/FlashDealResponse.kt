package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

class FlashDealResponse {
    @SerializedName("status")
    var status: Int? = 0

    @SerializedName("url")
    var url: String? = ""

    @SerializedName("tags")
    var tags: String? = ""

    @SerializedName("discount_percent")
    var discountPercent: Int? = 0

    @SerializedName("special_price")
    var specialPrice: Int? = 0

    @SerializedName("special_from_date")
    var specialFromDate: Int? = 0

    @SerializedName("from_date")
    var fromDate: String? = ""

    @SerializedName("special_to_date")
    var specialToDate: Int? = 0

    @SerializedName("progress")
    var progress: Progress? = null

    @SerializedName("product")
    var product: Product? = null

}