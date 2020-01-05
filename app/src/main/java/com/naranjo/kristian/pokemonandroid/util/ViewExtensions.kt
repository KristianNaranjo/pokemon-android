package com.naranjo.kristian.pokemonandroid.util

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

private const val CLICK_THROTTLE_MILLIS = 100L

fun View.toClickObservable(): Observable<View> =
        this.clicks()
            .throttleFirst(CLICK_THROTTLE_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { this }