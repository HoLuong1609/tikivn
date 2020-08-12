package com.tikivn.presentation.viewmodel

import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse
import java.util.concurrent.Callable

class BannerCallable : Callable<BaseResponse<Any>> {
    override fun call(): BaseResponse<Any> {
        val homeRepos = HomeReposImpl.getInstance()
        val response: BaseResponse<List<BannerResponse>>? =
            homeRepos.getBannerList().execute().body()
        return response!! as BaseResponse<Any>
    }
}