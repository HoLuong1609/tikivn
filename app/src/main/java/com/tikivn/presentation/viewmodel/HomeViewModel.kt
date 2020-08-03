package com.tikivn.presentation.viewmodel

import android.app.Application
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse
import com.tikivn.data.response.FlashDealResponse
import com.tikivn.extension.add
import com.tikivn.presentation.base.BaseViewModel
import com.tikivn.presentation.base.SingleLiveEvent
import com.tikivn.presentation.uimodel.FlashDealUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(application: Application, private val homeRepos: HomeRepos) :
    BaseViewModel(application) {

    val bannerLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val categoryLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val flashDealLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val bannerList = SingleLiveEvent<List<BannerResponse>>().apply { value = listOf() }
    val categoryList = SingleLiveEvent<List<CategoryResponse>>().apply { value = listOf() }
    val flashDealList = SingleLiveEvent<List<FlashDealUiModel>>().apply { value = listOf() }

    fun fetchData() {
        bannerList.value = listOf()
        categoryList.value = listOf()
        flashDealList.value = listOf()
        fetchBannerList { fetchQuickLinks { fetchFlashDeals() } }
    }

    private fun fetchBannerList(onFinally: () -> Unit) {
        homeRepos.getBannerList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                bannerLoading.value = true
            }.doFinally {
                bannerLoading.value = false
                onFinally()
            }
            .subscribe { onBannerResponse(it) }.add(subscriptions)
    }

    private fun fetchQuickLinks(onFinally: () -> Unit) {
        homeRepos.getQuickLinks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                categoryLoading.value = true
            }.doFinally {
                categoryLoading.value = false
                onFinally()
            }
            .subscribe { onCategoryResponse(it) }.add(subscriptions)
    }

    private fun fetchFlashDeals() {
        homeRepos.getFlashDeals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                categoryLoading.value = true
            }.doFinally {
                categoryLoading.value = false
            }
            .subscribe { onFlashDealResponse(it) }.add(subscriptions)
    }

    private fun onBannerResponse(response: BaseResponse<List<BannerResponse>>) {
        bannerList.value = response.data
    }

    private fun onCategoryResponse(response: BaseResponse<List<List<CategoryResponse>>>) {
        response.data?.let { data ->
            if (data.size >= 2) {
                val list = arrayListOf<CategoryResponse>()
                if (data[0].size <= data[1].size) {
                    data[0].forEachIndexed { index, _ ->
                        list.add(data[0][index])
                        list.add(data[1][index])
                    }
                    for (i in data[0].size until data[1].size) {
                        list.add(data[1][i])
                    }
                } else {
                    data[1].forEachIndexed { index, _ ->
                        list.add(data[0][index])
                        list.add(data[1][index])
                    }
                    for (i in data[1].size until data[0].size) {
                        list.add(data[0][i])
                    }
                }
                categoryList.value = list
            }
        }
    }

    private fun onFlashDealResponse(response: BaseResponse<List<FlashDealResponse>>) {
        response.data?.let { data ->
            flashDealList.value = data.map { FlashDealUiModel.from(it) }
        }
    }
}