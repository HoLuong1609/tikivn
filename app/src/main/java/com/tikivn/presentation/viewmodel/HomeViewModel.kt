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
    BaseViewModel(application) {

    val bannerLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val categoryLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val flashDealLoading = SingleLiveEvent<Boolean>().apply { value = false }
    val bannerList = SingleLiveEvent<List<BannerResponse>>().apply { value = listOf() }
    val categoryList = SingleLiveEvent<List<CategoryResponse>>().apply { value = listOf() }
    val flashDealList = SingleLiveEvent<List<FlashDealUiModel>>().apply { value = listOf() }

    private var asyncScheduler: AsyncScheduler<BaseResponse<Any>>
    private var syncScheduler: SyncScheduler<BaseResponse<Any>>

    init {
        asyncScheduler = AsyncScheduler(this::onAsyncTaskCompleted)
        asyncScheduler.addTask(BannerCallable())
        asyncScheduler.addTask(CategoryCallable())

        syncScheduler = SyncScheduler(this::onSyncTaskCompleted)
        syncScheduler.addTask(FlashCallable())
    }

    fun fetchData() {
        bannerList.value = listOf()
        categoryList.value = listOf()
        flashDealList.value = listOf()

        bannerLoading.value = true
        categoryLoading.value = true
        asyncScheduler.executeTasks()
    }

    private fun onAsyncTaskCompleted(results: List<BaseResponse<Any>?>) {
        val bannerResponse = results[0] as BaseResponse<List<BannerResponse>>?
        val categoryResponse = results[1] as BaseResponse<List<List<CategoryResponse>>>?
        onBannerResponse(bannerResponse)
        onCategoryResponse(categoryResponse)
        syncScheduler.executeTasks()
    }

    private fun onSyncTaskCompleted(results: List<BaseResponse<Any>?>) {
        val flashDealResponse = results[0] as BaseResponse<List<FlashDealResponse>>?
        onFlashDealResponse(flashDealResponse)
    }

    override fun onCleared() {
        super.onCleared()
        asyncScheduler.terminate()
        syncScheduler.terminate()
    }

    private fun onBannerResponse(response: BaseResponse<List<BannerResponse>>?) {
        bannerLoading.postValue(false)
        bannerList.postValue(response?.data)
    }

    private fun onCategoryResponse(response: BaseResponse<List<List<CategoryResponse>>>?) {
        categoryLoading.postValue(false)
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
                categoryList.postValue(list)
            }
        }
    }

    private fun onFlashDealResponse(response: BaseResponse<List<FlashDealResponse>>?) {
        flashDealLoading.postValue(false)
        response?.data?.let { data ->
            flashDealList.postValue(data.map { FlashDealUiModel.from(it) })
        }
    }
}