package com.naranjo.kristian.pokemonandroid.util

import android.view.View
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val CLICK_THROTTLE_MILLIS = 100L

fun View.toClickObservable(): Observable<View> =
    this.clicks()
        .throttleFirst(CLICK_THROTTLE_MILLIS, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .map { this }

fun RecyclerView.scrollToTopOnDataChanged(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    val compositeDisposable = CompositeDisposable()

    doOnAttach {
        compositeDisposable += adapter.listDataChanges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { scrollToPosition(0) }
    }

    doOnDetach { compositeDisposable.clear() }
}

fun RecyclerView.Adapter<RecyclerView.ViewHolder>.listDataChanges() =
    Observable.create<Unit> {
        val dataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                it.onNext(Unit)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                it.onNext(Unit)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                it.onNext(Unit)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                it.onNext(Unit)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                it.onNext(Unit)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                it.onNext(Unit)
            }
        }

        this.registerAdapterDataObserver(dataObserver)
        it.setDisposable(object : Disposable {
            var disposed = false

            override fun isDisposed() = disposed

            override fun dispose() {
                if (!disposed) {
                    this@listDataChanges.unregisterAdapterDataObserver(dataObserver)
                }
            }
        })
    }