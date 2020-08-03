package com.tikivn.presentation.viewmodel

import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse

interface OnBannerResponse {

    fun onBannerResponse(response: BaseResponse<List<BannerResponse>>?)
}