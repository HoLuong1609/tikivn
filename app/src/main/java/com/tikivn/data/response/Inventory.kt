package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

class Inventory {
    @SerializedName("product_virtual_type")
    var productVirtualType: Any? = null

    @SerializedName("fulfillment_type")
    var fulfillmentType: String? = ""

}