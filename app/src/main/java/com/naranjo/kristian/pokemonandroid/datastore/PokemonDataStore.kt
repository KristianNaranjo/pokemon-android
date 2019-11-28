package com.naranjo.kristian.pokemonandroid.datastore

interface PokemonDataStore {
    val pokemon: List<Pokemon>
}

class MockPokemonDataStore : PokemonDataStore {

    override val pokemon: List<Pokemon> = listOf(
        Pokemon("Charmander", listOf(Type.FIRE), ""),
        Pokemon("Bulbasaur", listOf(Type.GRASS), ""),
        Pokemon("Squirtle", listOf(Type.WATER), "")
    )
}