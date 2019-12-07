package com.naranjo.kristian.pokemonandroid

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.naranjo.kristian.pokemonandroid.service.PokemonJsonManager
import com.naranjo.kristian.pokemonandroid.service.PokemonJsonManagerImpl
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
        single<PokemonJsonManager> { PokemonJsonManagerImpl(androidContext()) }
        viewModel { PokedexViewModel(get()) }
    }
}