package com.tikivn.presentation.viewmodel

import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeReposImpl
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.Callable

abstract class BaseCallable<T>: Callable<T?> {
    val homeRepos = HomeReposImpl.getInstance()

    override fun call(): T? {
        return try {
            val response = fetchResponse()
            response.body()
        } catch (e: Exception) {
            null
        }
    }

    abstract fun fetchResponse(): Response<T>
}

class BannerCallable<T> : BaseCallable<T>() {

    override fun fetchResponse() = homeRepos.getBannerList().execute() as Response<T>
}

class FlashCallable<T> : BaseCallable<T>() {
    override fun fetchResponse() = homeRepos.getFlashDeals().execute() as Response<T>
}

class CategoryCallable<T> : BaseCallable<T>() {

    override fun fetchResponse() = homeRepos.getQuickLinks().execute() as Response<T>
}