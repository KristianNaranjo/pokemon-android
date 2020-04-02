package com.naranjo.kristian.pokemonandroid.service

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable

interface PokemonJsonManager {
    val pokemon: Observable<List<Pokemon>>
}

class PokemonJsonManagerImpl(context: Context) : PokemonJsonManager {
    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val jsonAdapter =
            moshi.adapter<List<Pokemon>>(Types.newParameterizedType(List::class.java, Pokemon::class.java))

    private val file = "pokemon.json"

    private val pokemonSrc = context.assets.open(file).bufferedReader().use { it.readText() }

    override val pokemon: Observable<List<Pokemon>> = Observable.fromCallable { jsonAdapter.fromJson(pokemonSrc) ?: listOf() }
}