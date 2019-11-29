package com.naranjo.kristian.pokemonandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore

class PokedexViewModel(private val pokemonDataStore: PokemonDataStore): ViewModel() {
    private val pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonListData: LiveData<List<Pokemon>> = pokemonList

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        pokemonList.value = pokemonDataStore.pokemon
    }
}