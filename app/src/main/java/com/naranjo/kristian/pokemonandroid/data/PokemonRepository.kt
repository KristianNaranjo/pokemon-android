package com.naranjo.kristian.pokemonandroid.data

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable

interface PokemonRepository {
    val pokemon: Observable<List<Pokemon>>
}

const val JSON_FILE = "pokemon.json"
class PokemonRepositoryImpl(context: Context) : PokemonRepository {
    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val jsonAdapter =
            moshi.adapter<List<Pokemon>>(Types.newParameterizedType(List::class.java, Pokemon::class.java))

    private val pokemonSrc = context.assets.open(JSON_FILE).bufferedReader().use { it.readText() }

    override val pokemon: Observable<List<Pokemon>> = Observable.fromCallable { jsonAdapter.fromJson(pokemonSrc) ?: listOf() }
}