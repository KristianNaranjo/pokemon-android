package com.naranjo.kristian.pokemonandroid

import android.app.Application
import com.naranjo.kristian.pokemonandroid.datastore.MockPokemonDataStore
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import com.naranjo.kristian.pokemonandroid.ui.pokedex.PokedexViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokemonApplication)
            modules(pokemonModule)
        }
    }

    private val pokemonModule = module {
        single<PokemonDataStore> { MockPokemonDataStore() }
        viewModel { PokedexViewModel(get()) }
    }
}