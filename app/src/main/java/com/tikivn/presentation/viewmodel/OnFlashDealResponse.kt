package com.tikivn.presentation.viewmodel

import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.FlashDealResponse

interface OnFlashDealResponse {

    fun onFlashDealResponse(response: BaseResponse<List<FlashDealResponse>>)
}