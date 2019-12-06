package com.naranjo.kristian.pokemonandroid.datastore

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

interface PokemonDataStore {
    val pokemon: List<Pokemon>
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