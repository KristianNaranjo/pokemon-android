package com.naranjo.kristian.pokemonandroid.util

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

private const val CLICK_THROTTLE_MILLIS = 100L

fun View.toClickObservable(): Observable<Unit> =
    this.clicks().throttleFirst(CLICK_THROTTLE_MILLIS, TimeUnit.MILLISECONDS)