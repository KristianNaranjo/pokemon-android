package com.naranjo.kristian.pokemonandroid.datastore

import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.service.PokemonJsonManager
import io.reactivex.Observable

interface PokemonDataStore {
    val pokemon: Observable<List<Pokemon>>
}

class PokemonDataStoreImpl(private val pokemonJsonManager: PokemonJsonManager) : PokemonDataStore {
    override val pokemon: Observable<List<Pokemon>>
        get() = pokemonJsonManager.pokemon
}
