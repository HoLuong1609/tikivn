package com.tikivn.presentation.viewmodel

import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeReposImpl
import java.util.concurrent.Callable

abstract class BaseCallable<T>: Callable<T> {
    val homeRepos = HomeReposImpl.getInstance()

    override fun call() = fetchData()

    abstract fun fetchData(): T
}