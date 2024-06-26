package com.example.buildProduct.base

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    private val progressList = mutableListOf<Int>()

    val progressStatus = ObservableField<Boolean>()

    fun startProgress(which: Int) {
        if (!progressList.contains(which))
            progressList.add(which)
        updateProgress()
    }

    fun endProgress(which: Int) {
        progressList.remove(which)
        updateProgress()
    }

    private fun updateProgress() {
        progressStatus.set(progressList.isNotEmpty())
    }

}