package com.naranjo.kristian.pokemonandroid.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naranjo.kristian.pokemonandroid.domain.PokemonUseCase
import com.naranjo.kristian.pokemonandroid.data.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PokemonDetailsViewModel(pokemon: Pokemon, pokemonUseCase: PokemonUseCase) : BaseViewModel() {

    val pokemonImagePosition: LiveData<Int> = MutableLiveData()
    val pokemonImageList: LiveData<List<PokemonImage>> = MutableLiveData()
    val currentPokemon: LiveData<Pokemon> = MutableLiveData()

    private val pokemonList = mutableListOf<Pokemon>()

    init {
        disposables += pokemonUseCase.pokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    pokemonList.clear()
                    pokemonList.addAll(it)
                    pokemonImageList.latestValue = pokemonList.map { PokemonImage(it.imageUrl) }
                    pokemonImagePosition.latestValue = pokemonList.indexOfFirst { it == pokemon }
                    currentPokemon.latestValue = pokemonList[pokemonImagePosition.value!!]
                }
            )
    }

    fun onPageUpdated(position: Int) {
        if (pokemonList.isNotEmpty()) {
            currentPokemon.latestValue = pokemonList[position]
        }
    }
}