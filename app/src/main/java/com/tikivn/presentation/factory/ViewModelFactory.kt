package com.tikivn.presentation.factory

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikivn.data.getInstance
import com.tikivn.data.repository.HomeRepos
import com.tikivn.data.repository.HomeReposImpl
import com.tikivn.presentation.viewmodel.HomeViewModel
import com.tikivn.presentation.viewmodel.MainViewModel

class ViewModelFactory(
    context: Context,
    private val homeRepos: HomeRepos
) : ViewModelProvider.NewInstanceFactory() {
    private val application = when (context) {
        is Activity -> context.application
        is Fragment -> context.requireActivity().application
        else -> throw IllegalStateException("unknown apllication: $context")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(application)
                }
                isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(application, homeRepos)
                }
                else -> throw IllegalStateException("unknown view model: $modelClass")
            }
        } as T


    companion object {
        fun getInstance(activity: Context): ViewModelFactory =
            ViewModelFactory(
                activity,
                HomeReposImpl.getInstance()
            )
    }
}