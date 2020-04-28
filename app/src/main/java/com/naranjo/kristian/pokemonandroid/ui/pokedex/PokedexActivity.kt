package com.naranjo.kristian.pokemonandroid.ui.pokedex

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.jakewharton.rxbinding3.widget.queryTextChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.databinding.ActivityPokedexBinding
import com.naranjo.kristian.pokemonandroid.databinding.PokemonRowItemBinding
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.*
import com.naranjo.kristian.pokemonandroid.ui.details.PokemonDetailsActivity
import com.naranjo.kristian.pokemonandroid.ui.widgets.LinearLayoutMarginItemDecoration
import com.naranjo.kristian.pokemonandroid.util.listDataChanges
import com.naranjo.kristian.pokemonandroid.util.scrollToTopOnDataChanged
import com.naranjo.kristian.pokemonandroid.util.toClickObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity : BaseActivity() {

    private val viewModel: PokedexViewModel by viewModel()

    private lateinit var binding: ActivityPokedexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonListAdapter = DelegatesAdapter(Pokemon::name, pokemonItemDelegate())

        with(binding.pokemonList) {
            adapter = pokemonListAdapter
            layoutManager = LinearLayoutManager(this@PokedexActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(
                LinearLayoutMarginItemDecoration(
                    resources.getDimension(R.dimen.pokedex_items_spacing).toInt(),
                    LinearLayoutMarginItemDecoration.Orientation.VERTICAL
                )
            )
            scrollToTopOnDataChanged(pokemonListAdapter)
        }

        with(viewModel) {
            pokemonListData.observe(
                this@PokedexActivity,
                Observer {
                    pokemonListAdapter.submitList(it)
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()

        disposables += binding.searchBar.queryTextChanges()
            .subscribe { viewModel.onSearchQueryEntered(it.toString()) }
        disposables += binding.searchBar.toClickObservable()
            .map { it as SearchView }
            .subscribe { it.isIconified = false }
    }

    private fun pokemonItemDelegate() =
        itemDelegate<Pokemon>(R.layout.pokemon_row_item)
            .click {
                val intent = Intent(this, PokemonDetailsActivity::class.java)
                intent.putExtra(PokemonDetailsActivity.EXTRA_POKEMON, it)
                startActivity(intent)
            }
            .bind {
                val binding = PokemonRowItemBinding.bind(itemView)
                with(binding) {
                    name.text = it.name
                    number.text = it.number
                    image.load(it.imageUrl)
                }
            }
}