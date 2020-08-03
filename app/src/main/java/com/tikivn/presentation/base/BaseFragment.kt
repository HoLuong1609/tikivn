package com.tikivn.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import com.tikivn.extension.showErrorMessage
import com.tikivn.presentation.factory.SchedulerProvider

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {
    private val subscriptions = CompositeDisposable()

    private lateinit var internalViewModel: VM
    private lateinit var viewBinding: VB

    protected val viewModel
        get() = internalViewModel

    val bindingView: VB
        get() = viewBinding

    abstract fun layoutId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalViewModel = onCreateViewModel()
        subscriptions.add(
            viewModel.getErrorMessage()
                .subscribeOn(SchedulerProvider.computation())
                .observeOn(SchedulerProvider.ui())
                .subscribe(this::showErrorDialog)
        )
    }

    private fun showErrorDialog(message: String) {
        context?.showErrorMessage(message){}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = DataBindingUtil.inflate(inflater,layoutId(), container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }

    abstract fun onCreateViewModel(): VM

}