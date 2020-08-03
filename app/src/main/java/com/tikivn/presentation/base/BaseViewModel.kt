package com.tikivn.presentation.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel(application: Application): AndroidViewModel(application), Observable {
    val context: Context = application.applicationContext
    val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }

    private val errorMessage: PublishSubject<String> = PublishSubject.create()
    fun getErrorMessage(): io.reactivex.Observable<String> = errorMessage

    protected fun showDialogError(message: String) {
        errorMessage.onNext(message)
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun handleError(e: Throwable) {
        Log.e("handleError","${e.message}")
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Suppress("unused")
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

}