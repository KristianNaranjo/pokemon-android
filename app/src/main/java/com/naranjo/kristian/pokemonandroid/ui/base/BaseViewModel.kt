package com.naranjo.kristian.pokemonandroid.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }

    // To reduce the amount of boilerplate needed when converting between LiveData and MutableLiveData
    // Works the same as MutableLiveData.value but allows declaration as LiveData
    // see https://github.com/IntrepidPursuits/skotlinton-android/pull/33#discussion_r275908063
    protected var <T : Any?> LiveData<T>.latestValue: T?
        get() = (this as MutableLiveData<T>).value
        set(value) {
            (this as MutableLiveData).value = value
        }

}