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
import java.util.concurrent.*

class HomeViewModel(application: Application, private val homeRepos: HomeRepos) :
    BaseViewModel(application), OnBannerResponse, OnCategoryResponse, OnFlashDealResponse {

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

        bannerLoading.value = true
        categoryLoading.value = true

        val simpleExecutor = SimpleExecutor()
        simpleExecutor.execute {
            val tasks: MutableList<Callable<BaseResponse<Any>>> = mutableListOf()
            tasks.add(BannerCallable())
            tasks.add(CategoryCallable())
            val executor: ExecutorService = Executors.newFixedThreadPool(2)
            try {
                val futures: List<Future<BaseResponse<Any>>> =
                    executor.invokeAll(tasks)
                mashupResult(futures)
                tasks.clear()
                tasks.add(FlashCallable())
                val futures2 = executor.invokeAll(tasks)
                onFlashDealResponse(futures2[0].get() as BaseResponse<List<FlashDealResponse>>)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            executor.shutdown()
        }
    }

    private fun mashupResult(futures: List<Future<BaseResponse<Any>>>) {
        val bannerResponse = futures[0].get() as BaseResponse<List<BannerResponse>>?
        val categoryResponse = futures[1].get() as BaseResponse<List<List<CategoryResponse>>>?
        onBannerResponse(bannerResponse)
        onCategoryResponse(categoryResponse)
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun onBannerResponse(response: BaseResponse<List<BannerResponse>>?) {
        bannerLoading.postValue(false)
        bannerList.postValue(response?.data)
    }

    override fun onCategoryResponse(response: BaseResponse<List<List<CategoryResponse>>>?) {
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

    override fun onFlashDealResponse(response: BaseResponse<List<FlashDealResponse>>?) {
        flashDealLoading.postValue(false)
        response?.data?.let { data ->
            flashDealList.postValue(data.map { FlashDealUiModel.from(it) })
        }
    }

    class SimpleExecutor : Executor {
        override fun execute(runnable: Runnable) {
            Thread(runnable).start()
        }
    }
}