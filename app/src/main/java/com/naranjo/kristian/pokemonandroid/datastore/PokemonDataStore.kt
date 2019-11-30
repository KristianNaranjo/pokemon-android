package com.naranjo.kristian.pokemonandroid.datastore

interface PokemonDataStore {
    val pokemon: List<Pokemon>
}

class MockPokemonDataStore : PokemonDataStore {

    override val pokemon: List<Pokemon> = listOf(
        Pokemon("Charmander", "#001", listOf(Type.FIRE), ""),
        Pokemon("Bulbasaur", "#002", listOf(Type.GRASS), ""),
        Pokemon("Squirtle", "#003", listOf(Type.WATER), "")
    )
}