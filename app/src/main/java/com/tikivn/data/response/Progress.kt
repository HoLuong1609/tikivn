package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

class Progress {
    @SerializedName("qty")
    var qty: Int? = 0

    @SerializedName("qty_ordered")
    var qtyOrdered: Int? = 0

    @SerializedName("qty_remain")
    var qtyRemain: Int? = 0

    @SerializedName("percent")
    var percent: Double? = 0.0

    @SerializedName("status")
    var status: String? = ""

}