package com.naranjo.kristian.pokemonandroid.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected val disposables = CompositeDisposable()

    override fun onStop() {
        disposables.clear()

        super.onStop()
    }
}
