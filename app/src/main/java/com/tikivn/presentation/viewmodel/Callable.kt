package com.tikivn.presentation.viewmodel

import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeReposImpl
import java.util.concurrent.Callable

abstract class BaseCallable<T>: Callable<T> {
    val homeRepos = HomeReposImpl.getInstance()

    override fun call() = fetchData()

    abstract fun fetchData(): T
}

class BannerCallable<T> : BaseCallable<T>() {

    override fun fetchData() = homeRepos.getBannerList().execute().body() as T
}

class FlashCallable<T> : BaseCallable<T>() {
    override fun fetchData() = homeRepos.getFlashDeals().execute().body() as T
}

class CategoryCallable<T> : BaseCallable<T>() {

    override fun fetchData() = homeRepos.getQuickLinks().execute().body() as T
}