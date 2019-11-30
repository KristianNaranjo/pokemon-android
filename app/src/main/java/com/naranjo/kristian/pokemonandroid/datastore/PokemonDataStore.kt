package com.naranjo.kristian.pokemonandroid.datastore

interface PokemonDataStore {
    val pokemon: List<Pokemon>
}

class MockPokemonDataStore : PokemonDataStore {

    override val pokemon: List<Pokemon> = listOf(
        Pokemon("Charmander", "#001", listOf(Type.FIRE), "https://miro.medium.com/max/858/1*Hxptm5gIRc3HyYXzw5nfpw.png"),
        Pokemon("Bulbasaur", "#002", listOf(Type.GRASS), ""),
        Pokemon("Squirtle", "#003", listOf(Type.WATER), "")
    )
}