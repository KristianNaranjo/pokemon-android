package com.naranjo.kristian.pokemonandroid

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.naranjo.kristian.pokemonandroid.domain.PokemonUseCase
import com.naranjo.kristian.pokemonandroid.domain.PokemonUseCaseImpl
import com.naranjo.kristian.pokemonandroid.data.Pokemon
import com.naranjo.kristian.pokemonandroid.data.PokemonRepository
import com.naranjo.kristian.pokemonandroid.data.PokemonRepositoryImpl
import com.naranjo.kristian.pokemonandroid.ui.details.PokemonDetailsViewModel
import com.naranjo.kristian.pokemonandroid.ui.pokedex.PokedexViewModel
import okhttp3.OkHttpClient
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

        Coil.setDefaultImageLoader {
            ImageLoader(applicationContext) {
                crossfade(true)
                okHttpClient {
                    OkHttpClient.Builder()
                            .cache(CoilUtils.createDefaultCache(applicationContext))
                            .build()
                }
            }
        }
    }

    private val pokemonModule = module {
        single<PokemonRepository> { PokemonRepositoryImpl(androidContext()) }
        single<PokemonUseCase> { PokemonUseCaseImpl(get()) }
        viewModel { PokedexViewModel(get()) }
        viewModel { (pokemon: Pokemon) -> PokemonDetailsViewModel(pokemon, get())}
    }
}