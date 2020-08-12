package com.tikivn.presentation.viewmodel

import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse
import java.util.concurrent.Callable

class CategoryCallable : Callable<BaseResponse<Any>> {

    override fun call(): BaseResponse<Any> {
        val homeRepos: HomeRepos = HomeReposImpl.getInstance()
        val response: BaseResponse<List<List<CategoryResponse>>>? =
            homeRepos.getQuickLinks().execute().body()
        return response!! as BaseResponse<Any>
    }
}