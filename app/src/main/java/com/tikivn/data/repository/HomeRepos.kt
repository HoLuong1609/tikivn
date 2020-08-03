package com.tikivn.data.repository

import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse
import com.tikivn.data.response.FlashDealResponse
import retrofit2.Call

interface HomeRepos {

    fun getBannerList(): Call<BaseResponse<List<BannerResponse>>>

    fun getQuickLinks(): Call<BaseResponse<List<List<CategoryResponse>>>>

    fun getFlashDeals(): Call<BaseResponse<List<FlashDealResponse>>>
}