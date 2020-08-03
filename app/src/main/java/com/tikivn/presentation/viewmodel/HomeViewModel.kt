package com.tikivn.presentation.viewmodel

import android.app.Application
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.response.BannerResponse
import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse
import com.tikivn.data.response.FlashDealResponse
import com.tikivn.presentation.base.BaseViewModel
import com.tikivn.presentation.base.SingleLiveEvent
import com.tikivn.presentation.uimodel.FlashDealUiModel

class HomeViewModel(application: Application, private val homeRepos: HomeRepos) :
    BaseViewModel(application), OnBannerResponse, OnCategoryResponse, OnFlashDealResponse {

    val bannerLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val categoryLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val flashDealLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val bannerList = SingleLiveEvent<List<BannerResponse>>().apply { value = listOf() }
    val categoryList = SingleLiveEvent<List<CategoryResponse>>().apply { value = listOf() }
    val flashDealList = SingleLiveEvent<List<FlashDealUiModel>>().apply { value = listOf() }
    @Volatile
    private var isFirstTaskComplete: Boolean = false
    private val bannerThread = FetchBannerThread(this)
    private val categoryThread = FetchCategoryThread( this)
    private val flashDealThread = FetchFlashDealThread(this)

    fun fetchData() {
        bannerList.value = listOf()
        categoryList.value = listOf()
        flashDealList.value = listOf()

        bannerThread.start()
        categoryThread.start()
        bannerLoading.value = true
        categoryLoading.value = true
    }

    override fun onCleared() {
        super.onCleared()
        bannerThread.quit()
        categoryThread.quit()
        flashDealThread.quit()
    }

    override fun onBannerResponse(response: BaseResponse<List<BannerResponse>>?) {
        bannerLoading.value = false
        if (isFirstTaskComplete) {
            loadFlashDeal()
        } else {
            isFirstTaskComplete = true
        }
        bannerList.value = response?.data
    }

    override fun onCategoryResponse(response: BaseResponse<List<List<CategoryResponse>>>?) {
        categoryLoading.value = false
        if (isFirstTaskComplete) {
            loadFlashDeal()
        } else {
            isFirstTaskComplete = true
        }
        response?.data?.let { data ->
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

    override fun onFlashDealResponse(response: BaseResponse<List<FlashDealResponse>>?) {
        flashDealLoading.value = false
        response?.data?.let { data ->
            flashDealList.value = data.map { FlashDealUiModel.from(it) }
        }
    }

    private fun loadFlashDeal() {
        flashDealThread.start()
    }
}