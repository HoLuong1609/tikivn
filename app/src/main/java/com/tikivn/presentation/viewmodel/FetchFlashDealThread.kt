package com.tikivn.presentation.viewmodel

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.FlashDealResponse

class FetchFlashDealThread(private val callback: OnFlashDealResponse) : HandlerThread(FetchFlashDealThread::class.simpleName) {

    override fun run() {
        val homeRepos: HomeRepos = HomeReposImpl.getInstance()
        val response: BaseResponse<List<FlashDealResponse>>?  = homeRepos.getFlashDeals().execute().body()
        Handler(Looper.getMainLooper()).post {
            callback.onFlashDealResponse(response)
        }
    }
}