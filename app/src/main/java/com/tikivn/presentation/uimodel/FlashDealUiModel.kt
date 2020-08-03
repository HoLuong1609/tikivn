package com.tikivn.presentation.uimodel

import com.tikivn.R
import com.tikivn.data.response.FlashDealResponse
import com.tikivn.extension.appContext
import java.text.DecimalFormat
import java.util.*

class FlashDealUiModel {
    var imageUrl: String? = ""
    var price: String? = ""
    var orderStatus: String? = ""
    var discountPercent: String? = ""

    companion object {
        fun from(entity: FlashDealResponse) = FlashDealUiModel().apply {
            imageUrl = entity.product?.thumbnailUrl
            val decFormat = DecimalFormat("#,###")
            price = String.format(Locale.getDefault(),
                appContext.getString(R.string.price_text),
                decFormat.format(entity.product?.price)).replace(",", ".")
            orderStatus = if (entity.progress?.qtyOrdered == 0) {
                appContext.getString(R.string.just_opened)
            } else {
                String.format(Locale.getDefault(), appContext.getString(R.string.sold_text), entity.progress?.qtyOrdered)
            }
            discountPercent = String.format(Locale.getDefault(), appContext.getString(R.string.discount_percent_text), entity.discountPercent)
        }
    }
}