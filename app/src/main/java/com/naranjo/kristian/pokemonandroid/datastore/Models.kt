package com.naranjo.kristian.pokemonandroid.datastore

import android.os.Parcelable
import com.naranjo.kristian.pokemonandroid.R
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    @Json(name = "id") val number: String,
    val types: List<Type>,
    @Json(name = "url") val imageUrl: String
) : Parcelable {
    enum class Type {
        @Json(name = "water")
        WATER {
            override val colorResId: Int = 0
        },
        @Json(name = "fire")
        FIRE {
            override val colorResId: Int = R.color.fireRed
        },
        @Json(name = "grass")
        GRASS {
            override val colorResId: Int = 0
        },
        @Json(name = "ice")
        ICE {
            override val colorResId: Int = R.color.iceBlue
        },
        @Json(name = "fighting")
        FIGHTING {
            override val colorResId: Int = 0
        },
        @Json(name = "dragon")
        DRAGON {
            override val colorResId: Int = R.color.dragonBlue
        },
        @Json(name = "flying")
        FLYING {
            override val colorResId: Int = 0
        },
        @Json(name = "electric")
        ELECTRIC {
            override val colorResId: Int = 0
        },
        @Json(name = "normal")
        NORMAL {
            override val colorResId: Int = 0
        },
        @Json(name = "fairy")
        FAIRY {
            override val colorResId: Int = R.color.fairyPink
        },
        @Json(name = "bug")
        BUG {
            override val colorResId: Int = R.color.bugGreen
        },
        @Json(name = "psychic")
        PSYCHIC {
            override val colorResId: Int = 0
        },
        @Json(name = "steel")
        STEEL {
            override val colorResId: Int = 0
        },
        @Json(name = "dark")
        DARK {
            override val colorResId: Int = R.color.darkBrown
        },
        @Json(name = "rock")
        ROCK {
            override val colorResId: Int = 0
        },
        @Json(name = "ground")
        GROUND {
            override val colorResId: Int = 0
        },
        @Json(name = "poison")
        POISON {
            override val colorResId: Int = R.color.poisonPurple
        },
        @Json(name = "ghost")
        GHOST {
            override val colorResId: Int = 0
        };

        abstract val colorResId: Int
    }
}
