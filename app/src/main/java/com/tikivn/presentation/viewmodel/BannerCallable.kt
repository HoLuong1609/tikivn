package com.tikivn.presentation.viewmodel

class BannerCallable<T> : BaseCallable<T>() {

    override fun fetchData() = homeRepos.getBannerList().execute().body() as T
}