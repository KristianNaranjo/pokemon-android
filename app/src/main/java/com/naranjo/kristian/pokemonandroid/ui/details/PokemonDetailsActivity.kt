package com.naranjo.kristian.pokemonandroid.ui.details

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import coil.api.load
import com.google.android.flexbox.JustifyContent
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.databinding.ActivityPokemonDetailsBinding
import com.naranjo.kristian.pokemonandroid.databinding.PokemonImageItemBinding
import com.naranjo.kristian.pokemonandroid.databinding.PokemonTypeEffectivenessItemBinding
import com.naranjo.kristian.pokemonandroid.databinding.PokemonTypeItemBinding
import com.naranjo.kristian.pokemonandroid.data.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import com.naranjo.kristian.pokemonandroid.ui.base.DelegatesAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.bind
import com.naranjo.kristian.pokemonandroid.ui.base.itemDelegate
import com.naranjo.kristian.pokemonandroid.ui.widgets.AlphaTransformer
import com.naranjo.kristian.pokemonandroid.ui.widgets.CustomFlexboxLayoutMananger
import com.naranjo.kristian.pokemonandroid.ui.widgets.ScaleTransformer
import com.naranjo.kristian.pokemonandroid.ui.widgets.TranslationTransformer
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class PokemonDetailsActivity : BaseActivity() {
    companion object {
        const val EXTRA_POKEMON = "extraPokemon"
    }

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var pokemonImages: ViewPager2

    private lateinit var imagesAdapter: DelegatesAdapter<PokemonImage>
    private lateinit var typesAdapter: DelegatesAdapter<TypeItem>
    private lateinit var weaknessesAdapter: DelegatesAdapter<TypeItem>
    private lateinit var strengthsAdapter: DelegatesAdapter<TypeItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        val pokemon = intent.getParcelableExtra(EXTRA_POKEMON) as Pokemon

        val viewModel = getViewModel<PokemonDetailsViewModel> { parametersOf(pokemon) }
        with(viewModel) {
            pokemonImageList.observe(
                this@PokemonDetailsActivity,
                Observer { imagesAdapter.submitList(it) }
            )
            pokemonImagePosition.observe(
                this@PokemonDetailsActivity,
                Observer { pokemonImages.setCurrentItem(it, false) }
            )
            currentPokemon.observe(
                this@PokemonDetailsActivity,
                Observer { bindData(it) }
            )
        }

        pokemonImages.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.onPageUpdated(position)
            }
        })
    }

    private fun initViews() {
        with(binding) {
            imagesAdapter = DelegatesAdapter(PokemonImage::url, pokemonImageDelegate())
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
                adapter = DelegatesAdapter<TypeItem>(TypeItem::type, pokemonTypeDelegate()).apply {
                    typesAdapter = this
                }
            }
            with(detailsWeaknesses) {
                layoutManager = CustomFlexboxLayoutMananger(this@PokemonDetailsActivity)
                adapter = DelegatesAdapter<TypeItem>(
                    TypeItem::type,
                    pokemonTypeEffectivenessDelegate()
                ).apply { weaknessesAdapter = this }
            }
            with(detailsStrengths) {
                layoutManager = CustomFlexboxLayoutMananger(this@PokemonDetailsActivity)
                adapter = DelegatesAdapter<TypeItem>(
                    TypeItem::type,
                    pokemonTypeEffectivenessDelegate()
                ).apply { strengthsAdapter = this }
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

    private fun pokemonImageDelegate() =
        itemDelegate<PokemonImage>(R.layout.pokemon_image_item)
            .bind {
                val binding = PokemonImageItemBinding.bind(itemView)
                binding.detailsImage.load(it.url)
            }

    private fun pokemonTypeDelegate() =
        itemDelegate<TypeOnly>(R.layout.pokemon_type_item)
            .bind {
                val binding = PokemonTypeItemBinding.bind(itemView)
                val type = it.type
                with(binding) {
                    pokemonType.text = type.name
                    pokemonType.background.colorFilter = PorterDuffColorFilter(
                        binding.pokemonType.context.getColor(type.colorResId),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }

    private fun pokemonTypeEffectivenessDelegate() =
        itemDelegate<TypeEffectivenessItem>(R.layout.pokemon_type_effectiveness_item)
            .bind {
                val binding = PokemonTypeEffectivenessItemBinding.bind(itemView)
                val type = it.type

                binding.type.text = type.name
                itemView.background.colorFilter = PorterDuffColorFilter(
                    itemView.context.getColor(type.colorResId),
                    PorterDuff.Mode.SRC_ATOP
                )

                val effectiveness = it.effectiveness
                if ((effectiveness < Pokemon.Type.EFFECTIVE || effectiveness > Pokemon.Type.SUPER_EFFECTIVE) && effectiveness != Pokemon.Type.NO_EFFECT) {
                    binding.effectiveness.isVisible = true
                    binding.effectiveness.text =
                        itemView.resources.getString(R.string.effectiveness, effectiveness)
                } else {
                    binding.effectiveness.isVisible = false
                }
            }
}

data class PokemonImage(val url: String)

sealed class TypeItem(open val type: Pokemon.Type)
data class TypeOnly(override val type: Pokemon.Type) : TypeItem(type)
data class TypeEffectivenessItem(override val type: Pokemon.Type, val effectiveness: Float) :
    TypeItem(type)