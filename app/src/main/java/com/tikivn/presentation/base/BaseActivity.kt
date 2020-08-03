package com.tikivn.presentation.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tikivn.presentation.factory.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<VM : BaseViewModel, VB : ViewDataBinding> : AppCompatActivity() {
    private lateinit var internalViewModel: VM
    private lateinit var viewBinding: VB
    val viewModel: VM
        get() = internalViewModel

    val bindingView: VB
        get() = viewBinding

    abstract fun onCreateViewModel(): VM

    abstract fun layoutId(): Int
    abstract fun initView()
    private val messageSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId())
        internalViewModel = onCreateViewModel()
        messageSubscriptions.add(
            viewModel.getErrorMessage()
                .subscribeOn(SchedulerProvider.computation())
                .observeOn(SchedulerProvider.ui())
                .subscribe(this::showErrorDialog)
        )
        initView()
    }

    private fun showErrorDialog(message: String) {
        this.showErrorDialog(message)
    }

    fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            currentFocus?.let {
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}