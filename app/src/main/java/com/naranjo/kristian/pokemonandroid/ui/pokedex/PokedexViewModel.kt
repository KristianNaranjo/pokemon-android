package com.naranjo.kristian.pokemonandroid.ui.pokedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class PokedexViewModel(private val pokemonDataStore: PokemonDataStore) : ViewModel() {
    private val pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonListData: LiveData<List<Pokemon>> = pokemonList

    private val searchQuerySubject = PublishSubject.create<String>()
    private var fullPokemonList: List<Pokemon>? = null

    private val disposables = CompositeDisposable()

    init {
        loadPokemon()
        observeQuery()
    }

    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }

    fun onSearchQueryEntered(query: String) {
        searchQuerySubject.onNext(query)
    }

    private fun observeQuery() {
        disposables += searchQuerySubject
            .skip(1)
            .debounce(250L, TimeUnit.MILLISECONDS)
            .switchMap { Observable.fromCallable { filterPokemon(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { filteredPokemon -> pokemonList.value = filteredPokemon }
    }

    private fun filterPokemon(query: String): List<Pokemon> =
        if (query.isBlank()) {
            fullPokemonList ?: listOf()
        } else {
            fullPokemonList?.filter { it.name.contains(query, ignoreCase = true) } ?: listOf()
        }

    private fun loadPokemon() {
        fullPokemonList = pokemonDataStore.pokemon
        pokemonList.value = fullPokemonList
    }
}
