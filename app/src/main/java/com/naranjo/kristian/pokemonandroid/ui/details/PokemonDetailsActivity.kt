package com.naranjo.kristian.pokemonandroid.ui.details

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager
import com.jakewharton.rxbinding3.recyclerview.dataChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
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

    private val flexboxLayoutManager
        get() = object : FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

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

        val flexboxItemDecoration = FlexboxItemDecoration(this@PokemonDetailsActivity).apply {
            setDrawable(getDrawable(R.drawable.pokemon_details_flexbox_divider))
        }
        weaknesses.apply {
            layoutManager = flexboxLayoutManager
            adapter = PokemonTypesAdapter().apply { weaknessesAdapter = this }
            addItemDecoration(flexboxItemDecoration)
        }
        strengths.apply {
            layoutManager = flexboxLayoutManager
            adapter = PokemonTypesAdapter().apply { strengthsAdapter = this }
            addItemDecoration(flexboxItemDecoration)
        }
        types.apply {
            layoutManager = flexboxLayoutManager
            adapter = PokemonTypesAdapter().apply { typesAdapter = this }
            addItemDecoration(flexboxItemDecoration)
        }

        bindData(pokemon)
    }

    override fun bindViews() {
        name = findViewById(R.id.details_name)
        pokemonImages = findViewById(R.id.images)
        number = findViewById(R.id.details_number)
        types = findViewById(R.id.details_types)
        weaknesses = findViewById(R.id.details_weaknesses)
        strengths = findViewById(R.id.details_strengths)
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