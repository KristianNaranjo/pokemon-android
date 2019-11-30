package com.naranjo.kristian.pokemonandroid.datastore

import android.content.Context
import com.naranjo.kristian.pokemonandroid.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

interface PokemonDataStore {
    val pokemon: List<Pokemon>
}

class MockPokemonDataStore : PokemonDataStore {
    override val pokemon: List<Pokemon> = listOf(
        Pokemon(
            "Charmander",
            "#001",
            listOf(Type.FIRE),
            "https://miro.medium.com/max/858/1*Hxptm5gIRc3HyYXzw5nfpw.png"
        ),
        Pokemon("Bulbasaur", "#002", listOf(Type.GRASS), ""),
        Pokemon("Squirtle", "#003", listOf(Type.WATER), "")
    )
}

class PokemonDataStoreImpl(context: Context) : PokemonDataStore {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val jsonAdapter =
        moshi.adapter<List<Pokemon>>(Types.newParameterizedType(List::class.java, Pokemon::class.java))

    private val file = "pokemon.json"

    private val pokemonSrc = context.assets.open(file).bufferedReader().use { it.readText() }

    override val pokemon: List<Pokemon> = jsonAdapter.fromJson(pokemonSrc)!!
}