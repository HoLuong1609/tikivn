package com.tikivn.presentation.viewmodel

import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.FlashDealResponse
import java.util.concurrent.Callable

class FlashCallable : Callable<BaseResponse<Any>> {
    override fun call(): BaseResponse<Any> {
        val homeRepos: HomeRepos = HomeReposImpl.getInstance()
        val response: BaseResponse<List<FlashDealResponse>>?  = homeRepos.getFlashDeals().execute().body()
        return response!! as BaseResponse<Any>
    }
}