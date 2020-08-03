package com.tikivn.data.network.api

import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse
import com.tikivn.data.response.FlashDealResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface HomeService {

    @GET("/v2/home/banners/v2")
    fun getBannerList(): Observable<BaseResponse<List<BannerResponse>>>

    @GET("/shopping/v2/widgets/quick_link")
    fun getQuickLinks(): Observable<BaseResponse<List<List<CategoryResponse>>>>

    @GET("/v2/widget/deals/hot")
    fun getFlashDeals(): Observable<BaseResponse<List<FlashDealResponse>>>
}