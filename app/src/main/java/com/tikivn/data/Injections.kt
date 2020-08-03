package com.tikivn.data

import com.tikivn.data.network.api.HomeService
import com.tikivn.data.repository.HomeReposImpl

fun HomeReposImpl.Companion.getInstance() =
    getInstance(RetrofitClient.getInstance()[HomeService::class.java])
