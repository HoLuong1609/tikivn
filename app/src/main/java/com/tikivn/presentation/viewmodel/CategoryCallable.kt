package com.tikivn.presentation.viewmodel

class CategoryCallable<T> : BaseCallable<T>() {

    override fun fetchData() = homeRepos.getQuickLinks().execute().body() as T
}