package com.tikivn.data.repository

import com.tikivn.data.response.BannerResponse
import io.reactivex.Observable
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse
import com.tikivn.data.response.FlashDealResponse

interface HomeRepos {

    fun getBannerList(): Observable<BaseResponse<List<BannerResponse>>>

    fun getQuickLinks(): Observable<BaseResponse<List<List<CategoryResponse>>>>

    fun getFlashDeals(): Observable<BaseResponse<List<FlashDealResponse>>>
}