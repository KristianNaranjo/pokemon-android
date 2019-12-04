package com.naranjo.kristian.pokemonandroid.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class PokedexViewModel(private val pokemonDataStore: PokemonDataStore) : BaseViewModel() {
    companion object {
        const val DEBOUNCE_TIMEOUT_MILLIS = 350L
    }

    private val pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonListData: LiveData<List<Pokemon>> = pokemonList

    private val searchQuerySubject = PublishSubject.create<String>()
    private var fullPokemonList = mutableListOf<Pokemon>()

    init {
        loadPokemon()
        observeQuery()
    }

    fun onSearchQueryEntered(query: String) {
        searchQuerySubject.onNext(query)
    }

    private fun loadPokemon() {
        fullPokemonList.addAll(pokemonDataStore.pokemon)
        pokemonList.value = fullPokemonList
    }

    private fun observeQuery() {
        disposables += searchQuerySubject
                .skip(1)
                .debounce(DEBOUNCE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { Observable.fromCallable { filterPokemon(it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { filteredPokemon -> pokemonList.value = filteredPokemon }
    }

    private fun filterPokemon(query: String): List<Pokemon> =
            if (query.isBlank()) {
                fullPokemonList
            } else {
                fullPokemonList.filter { it.name.contains(query, ignoreCase = true) }
            }
}
