package com.tikivn.data.repository

import com.tikivn.data.network.api.HomeService

class HomeReposImpl private constructor(private val service: HomeService) : HomeRepos {

    override fun getBannerList() = service.getBannerList()

    override fun getQuickLinks() = service.getQuickLinks()

    override fun getFlashDeals() = service.getFlashDeals()

    companion object {
        private var INSTANCE: HomeReposImpl? = null

        fun getInstance(service: HomeService): HomeReposImpl = INSTANCE
            ?: HomeReposImpl(service).also {
                INSTANCE = it
            }
    }
}