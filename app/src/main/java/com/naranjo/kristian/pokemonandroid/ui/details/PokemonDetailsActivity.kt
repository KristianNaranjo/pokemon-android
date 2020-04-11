package com.naranjo.kristian.pokemonandroid.ui.details

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.*
import com.jakewharton.rxbinding3.recyclerview.dataChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import com.naranjo.kristian.pokemonandroid.ui.widgets.AlphaTransformer
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

    private lateinit var number: TextView
    private lateinit var pokemonImages: ViewPager2
    private lateinit var name: TextView
    private lateinit var types: RecyclerView
    private lateinit var weaknesses: RecyclerView
    private lateinit var strengths: RecyclerView

    private lateinit var typesAdapter: PokemonTypesAdapter
    private lateinit var weaknessesAdapter: PokemonTypesAdapter
    private lateinit var strengthsAdapter: PokemonTypesAdapter

    override val layoutResId: Int = R.layout.activity_pokemon_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemon = intent.getParcelableExtra(EXTRA_POKEMON) as Pokemon
        val pokemonImagesAdapter = PokemonImagesAdapter()
        pokemonImages.adapter = pokemonImagesAdapter

        val pokemonList = mutableListOf<Pokemon>()
        disposables += pokemonDataStore.pokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                pokemonImagesAdapter.submitList(it.map { PokemonImage(it.imageUrl) })
                pokemonList.addAll(it)
                pokemonImagesAdapter.dataChanges()
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

    override fun bindViews() {
        name = findViewById(R.id.details_name)
        pokemonImages = findViewById<ViewPager2>(R.id.images).apply {
            offscreenPageLimit = 3
            setPageTransformer(CompositePageTransformer().apply {
                val offsetPx = resources.getDimensionPixelOffset(R.dimen.details_image_offset)
                val marginPx = resources.getDimensionPixelOffset(R.dimen.details_image_page_margin)
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
        number = findViewById(R.id.details_number)

        fun flexboxLayoutManager() =
            object : FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

        types = findViewById<RecyclerView>(R.id.details_types).apply {
            layoutManager = flexboxLayoutManager().apply { justifyContent = JustifyContent.CENTER }
            adapter = PokemonTypesAdapter().apply { typesAdapter = this }
        }
        weaknesses = findViewById<RecyclerView>(R.id.details_weaknesses).apply {
            layoutManager = flexboxLayoutManager()
            adapter = PokemonTypesAdapter().apply { weaknessesAdapter = this }
        }
        strengths = findViewById<RecyclerView>(R.id.details_strengths).apply {
            layoutManager = flexboxLayoutManager()
            adapter = PokemonTypesAdapter().apply { strengthsAdapter = this }
        }
    }

    private fun bindData(pokemon: Pokemon) {
        number.text = pokemon.number
        name.text = pokemon.name
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