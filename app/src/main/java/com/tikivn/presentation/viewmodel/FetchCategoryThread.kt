package com.tikivn.presentation.viewmodel

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse

class FetchCategoryThread(private val callback: OnCategoryResponse) : HandlerThread(FetchCategoryThread::class.java.simpleName) {

    override fun run() {
        val homeRepos: HomeRepos = HomeReposImpl.getInstance()
        val response: BaseResponse<List<List<CategoryResponse>>>?  = homeRepos.getQuickLinks().execute().body()
        response?.let {
            Handler(Looper.getMainLooper()).post {
                callback.onCategoryResponse(it)
            }
        }
    }
}