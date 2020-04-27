package com.naranjo.kristian.pokemonandroid.ui.details

import android.os.Bundle
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.JustifyContent
import com.jakewharton.rxbinding3.recyclerview.dataChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.databinding.ActivityPokemonDetailsBinding
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import com.naranjo.kristian.pokemonandroid.ui.widgets.AlphaTransformer
import com.naranjo.kristian.pokemonandroid.ui.widgets.CustomFlexboxLayoutMananger
import com.naranjo.kristian.pokemonandroid.ui.widgets.ScaleTransformer
import com.naranjo.kristian.pokemonandroid.ui.widgets.TranslationTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


class PokemonDetailsActivity : BaseActivity() {
    companion object {
        const val EXTRA_POKEMON = "extraPokemon"
    }

    private val pokemonDataStore: PokemonDataStore by inject()

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var pokemonImages: ViewPager2

    private lateinit var imagesAdapter: PokemonImagesAdapter
    private lateinit var typesAdapter: PokemonTypesAdapter
    private lateinit var weaknessesAdapter: PokemonTypesAdapter
    private lateinit var strengthsAdapter: PokemonTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        val pokemon = intent.getParcelableExtra(EXTRA_POKEMON) as Pokemon

        val pokemonList = mutableListOf<Pokemon>()
        disposables += pokemonDataStore.pokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                imagesAdapter.submitList(it.map { PokemonImage(it.imageUrl) })
                pokemonList.addAll(it)
                imagesAdapter.dataChanges()
            }
            .subscribe {
                pokemonImages.setCurrentItem(pokemonList.indexOfFirst { it == pokemon }, false)
            }

        pokemonImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                bindData(pokemonList[pokemonImages.currentItem])
            }
        })

        bindData(pokemon)
    }

    private fun initViews() {
        with(binding) {
            imagesAdapter = PokemonImagesAdapter()
            pokemonImages = images.apply {
                adapter = imagesAdapter
                offscreenPageLimit = 3
                setPageTransformer(CompositePageTransformer().apply {
                    val offsetPx = resources.getDimensionPixelOffset(R.dimen.details_image_offset)
                    val marginPx =
                        resources.getDimensionPixelOffset(R.dimen.details_image_page_margin)
                    addTransformer(
                        TranslationTransformer(
                            offsetPx,
                            marginPx,
                            ViewPager2.ORIENTATION_HORIZONTAL
                        )
                    )
                    addTransformer(AlphaTransformer(.6f))
                    addTransformer(ScaleTransformer(.4f))
                })
            }

            with(detailsTypes) {
                layoutManager = CustomFlexboxLayoutMananger(this@PokemonDetailsActivity).apply {
                    justifyContent = JustifyContent.CENTER
                }
                adapter = PokemonTypesAdapter().apply { typesAdapter = this }
            }
            with(detailsWeaknesses) {
                layoutManager = CustomFlexboxLayoutMananger(this@PokemonDetailsActivity)
                adapter = PokemonTypesAdapter().apply { weaknessesAdapter = this }
            }
            with(detailsStrengths) {
                layoutManager = CustomFlexboxLayoutMananger(this@PokemonDetailsActivity)
                adapter = PokemonTypesAdapter().apply { strengthsAdapter = this }
            }
        }
    }

    private fun bindData(pokemon: Pokemon) {
        with(binding) {
            detailsNumber.text = pokemon.number
            detailsName.text = pokemon.name
            typesAdapter.submitList(pokemon.types.map { TypeOnly(it) })
            weaknessesAdapter.submitList(
                Pokemon.Type.calculateWeaknesses(pokemon.types).map {
                    TypeEffectivenessItem(it.key, it.value)
                }
            )
            strengthsAdapter.submitList(Pokemon.Type.calculateStrengths(pokemon.types).map {
                TypeEffectivenessItem(it.key, it.value)
            })
        }
    }
}