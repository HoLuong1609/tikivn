package com.tikivn.data.response

import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("sku")
    var sku: Any? = null

    @SerializedName("name")
    var name: String? = ""

    @SerializedName("url_path")
    var urlPath: String? = ""

    @SerializedName("price")
    var price: Int? = 0

    @SerializedName("list_price")
    var listPrice: Int? = 0

    @SerializedName("badges")
    var badges: List<Any> = listOf()

    @SerializedName("discount")
    var discount: Int? = 0

    @SerializedName("rating_average")
    var ratingAverage: Int? = 0

    @SerializedName("review_count")
    var reviewCount: Int? = 0

    @SerializedName("order_count")
    var orderCount: Int? = 0

    @SerializedName("thumbnail_url")
    var thumbnailUrl: String? = ""

    @SerializedName("is_visible")
    var isVisible: Boolean? = false

    @SerializedName("is_fresh")
    var isFresh: Boolean? = false

    @SerializedName("is_flower")
    var isFlower: Boolean? = false

    @SerializedName("is_gift_card")
    var isGiftCard: Boolean? = false

    @SerializedName("inventory")
    var inventory: Inventory? = null

    @SerializedName("url_attendant_input_form")
    var urlAttendantInputForm: String? = ""

    @SerializedName("master_id")
    var masterId: Int? = 0

    @SerializedName("seller_product_id")
    var sellerProductId: Int? = 0

    @SerializedName("price_prefix")
    var pricePrefix: String? = ""

}