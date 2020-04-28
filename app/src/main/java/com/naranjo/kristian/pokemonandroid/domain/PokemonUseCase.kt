package com.naranjo.kristian.pokemonandroid.domain

import com.naranjo.kristian.pokemonandroid.data.Pokemon
import com.naranjo.kristian.pokemonandroid.data.PokemonRepository
import io.reactivex.Observable

interface PokemonUseCase {
    val pokemon: Observable<List<Pokemon>>
}

class PokemonUseCaseImpl(private val pokemonRepository: PokemonRepository) : PokemonUseCase {
    override val pokemon: Observable<List<Pokemon>>
        get() = pokemonRepository.pokemon
}
