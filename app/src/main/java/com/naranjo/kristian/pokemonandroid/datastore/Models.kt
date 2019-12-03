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
        @Json(name = "bug")
        BUG {
            override val colorResId: Int = R.color.bugGreen
        },
        @Json(name = "dark")
        DARK {
            override val colorResId: Int = R.color.darkBrown
        },
        @Json(name = "dragon")
        DRAGON {
            override val colorResId: Int = R.color.dragonBlue
        },
        @Json(name = "electric")
        ELECTRIC {
            override val colorResId: Int = R.color.electricYellow
        },
        @Json(name = "fairy")
        FAIRY {
            override val colorResId: Int = R.color.fairyPink
        },
        @Json(name = "fighting")
        FIGHTING {
            override val colorResId: Int = R.color.fightingMaroon
        },
        @Json(name = "fire")
        FIRE {
            override val colorResId: Int = R.color.fireRed
        },
        @Json(name = "flying")
        FLYING {
            override val colorResId: Int = R.color.flyingPurple
        },
        @Json(name = "ghost")
        GHOST {
            override val colorResId: Int = R.color.ghostPurple
        },
        @Json(name = "grass")
        GRASS {
            override val colorResId: Int = R.color.grassGreen
        },
        @Json(name = "ground")
        GROUND {
            override val colorResId: Int = R.color.groundBeige
        },
        @Json(name = "ice")
        ICE {
            override val colorResId: Int = R.color.iceBlue
        },
        @Json(name = "normal")
        NORMAL {
            override val colorResId: Int = R.color.normalBrown
        },
        @Json(name = "poison")
        POISON {
            override val colorResId: Int = R.color.poisonPurple
        },
        @Json(name = "psychic")
        PSYCHIC {
            override val colorResId: Int = R.color.psychicPink
        },
        @Json(name = "rock")
        ROCK {
            override val colorResId: Int = R.color.rockBrown
        },
        @Json(name = "steel")
        STEEL {
            override val colorResId: Int = R.color.steelGrey
        },
        @Json(name = "water")
        WATER {
            override val colorResId: Int = R.color.waterBlue
        };

        abstract val colorResId: Int
    }
}
