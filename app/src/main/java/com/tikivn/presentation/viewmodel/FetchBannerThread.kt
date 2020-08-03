package com.tikivn.presentation.viewmodel

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse

class FetchBannerThread(private val callback: OnBannerResponse) :
    HandlerThread(FetchBannerThread::class.simpleName) {

    override fun run() {
        val homeRepos = HomeReposImpl.getInstance()
        val response: BaseResponse<List<BannerResponse>>? = homeRepos.getBannerList().execute().body()
        response?.let {
            Handler(Looper.getMainLooper()).post {
                callback.onBannerResponse(it)
            }
        }

    }
}