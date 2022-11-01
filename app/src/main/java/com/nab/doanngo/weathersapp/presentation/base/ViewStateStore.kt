package com.nab.doanngo.weathersapp.presentation.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map

class ViewStateStore<T : Any>(
    initialState: T
) : StoreObservable<T> {
    private val stateLiveData = MutableLiveData<T>().apply {
        value = initialState
    }

    val state: T
        get() = stateLiveData.value!! // It's non-null because have added initial state already

    override fun <S> observe(
        owner: LifecycleOwner,
        selector: (T) -> S,
        observer: OnChangedObserver<S>
    ) {
        stateLiveData.map(selector)
            .distinctUntilChanged()
            .observe(owner) { state -> observer(state) }
    }

    override fun <S> observeForever(selector: (T) -> S, observer: OnChangedObserver<S>) {
        stateLiveData.map(selector)
            .distinctUntilChanged()
            .observeForever { state -> observer(state) }
    }

    fun dispatchState(state: T) {
        stateLiveData.value = state
    }
}

interface StoreObservable<T : Any> {
    fun <S> observe(
        owner: LifecycleOwner,
        selector: (T) -> S,
        observer: OnChangedObserver<S>
    )

    fun <S> observeForever(
        selector: (T) -> S,
        observer: OnChangedObserver<S>
    )
}

typealias OnChangedObserver<T> = (T) -> Unit
