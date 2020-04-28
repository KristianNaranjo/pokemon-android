package com.naranjo.kristian.pokemonandroid.data

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
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to SUPER_EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to NOT_VERY_EFFECTIVE,
                    FIGHTING to NOT_VERY_EFFECTIVE,
                    FIRE to NOT_VERY_EFFECTIVE,
                    FLYING to NOT_VERY_EFFECTIVE,
                    GHOST to NOT_VERY_EFFECTIVE,
                    GRASS to SUPER_EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to NOT_VERY_EFFECTIVE,
                    PSYCHIC to SUPER_EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "dark")
        DARK {
            override val colorResId: Int = R.color.darkBrown
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to NOT_VERY_EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to NOT_VERY_EFFECTIVE,
                    FIGHTING to NOT_VERY_EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to SUPER_EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to SUPER_EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "dragon")
        DRAGON {
            override val colorResId: Int = R.color.dragonBlue
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to SUPER_EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to NO_EFFECT,
                    FIGHTING to EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "electric")
        ELECTRIC {
            override val colorResId: Int = R.color.electricYellow
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to NOT_VERY_EFFECTIVE,
                    ELECTRIC to NOT_VERY_EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to SUPER_EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to NOT_VERY_EFFECTIVE,
                    GROUND to NO_EFFECT,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to EFFECTIVE,
                    WATER to SUPER_EFFECTIVE
                )
            }
        },
        @Json(name = "fairy")
        FAIRY {
            override val colorResId: Int = R.color.fairyPink
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to SUPER_EFFECTIVE,
                    DRAGON to SUPER_EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to SUPER_EFFECTIVE,
                    FIRE to NOT_VERY_EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to NOT_VERY_EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "fighting")
        FIGHTING {
            override val colorResId: Int = R.color.fightingMaroon
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to NOT_VERY_EFFECTIVE,
                    DARK to SUPER_EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to NOT_VERY_EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to NOT_VERY_EFFECTIVE,
                    GHOST to NO_EFFECT,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to SUPER_EFFECTIVE,
                    NORMAL to SUPER_EFFECTIVE,
                    POISON to NOT_VERY_EFFECTIVE,
                    PSYCHIC to NOT_VERY_EFFECTIVE,
                    ROCK to SUPER_EFFECTIVE,
                    STEEL to SUPER_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "fire")
        FIRE {
            override val colorResId: Int = R.color.fireRed
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to SUPER_EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to NOT_VERY_EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to NOT_VERY_EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to SUPER_EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to SUPER_EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to NOT_VERY_EFFECTIVE,
                    STEEL to SUPER_EFFECTIVE,
                    WATER to NOT_VERY_EFFECTIVE
                )
            }
        },
        @Json(name = "flying")
        FLYING {
            override val colorResId: Int = R.color.flyingPurple
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to SUPER_EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to NOT_VERY_EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to SUPER_EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to SUPER_EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to NOT_VERY_EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "ghost")
        GHOST {
            override val colorResId: Int = R.color.ghostPurple
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to NOT_VERY_EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to SUPER_EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to NO_EFFECT,
                    POISON to EFFECTIVE,
                    PSYCHIC to SUPER_EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "grass")
        GRASS {
            override val colorResId: Int = R.color.grassGreen
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to NOT_VERY_EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to NOT_VERY_EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to NOT_VERY_EFFECTIVE,
                    FLYING to NOT_VERY_EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to NOT_VERY_EFFECTIVE,
                    GROUND to SUPER_EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to NOT_VERY_EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to SUPER_EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to SUPER_EFFECTIVE
                )
            }
        },
        @Json(name = "ground")
        GROUND {
            override val colorResId: Int = R.color.groundBeige
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to NOT_VERY_EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to SUPER_EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to SUPER_EFFECTIVE,
                    FLYING to NO_EFFECT,
                    GHOST to EFFECTIVE,
                    GRASS to NOT_VERY_EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to SUPER_EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to SUPER_EFFECTIVE,
                    STEEL to SUPER_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "ice")
        ICE {
            override val colorResId: Int = R.color.iceBlue
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to SUPER_EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to NOT_VERY_EFFECTIVE,
                    FLYING to SUPER_EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to SUPER_EFFECTIVE,
                    GROUND to SUPER_EFFECTIVE,
                    ICE to NOT_VERY_EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to NOT_VERY_EFFECTIVE
                )
            }
        },
        @Json(name = "normal")
        NORMAL {
            override val colorResId: Int = R.color.normalBrown
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to NO_EFFECT,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to NOT_VERY_EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "poison")
        POISON {
            override val colorResId: Int = R.color.poisonPurple
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to SUPER_EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to NOT_VERY_EFFECTIVE,
                    GRASS to SUPER_EFFECTIVE,
                    GROUND to NOT_VERY_EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to NOT_VERY_EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to NOT_VERY_EFFECTIVE,
                    STEEL to NO_EFFECT,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "psychic")
        PSYCHIC {
            override val colorResId: Int = R.color.psychicPink
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to NO_EFFECT,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to SUPER_EFFECTIVE,
                    FIRE to EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to SUPER_EFFECTIVE,
                    PSYCHIC to NOT_VERY_EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "rock")
        ROCK {
            override val colorResId: Int = R.color.rockBrown
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to SUPER_EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to NOT_VERY_EFFECTIVE,
                    FIRE to SUPER_EFFECTIVE,
                    FLYING to SUPER_EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to NOT_VERY_EFFECTIVE,
                    ICE to SUPER_EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to EFFECTIVE
                )
            }
        },
        @Json(name = "steel")
        STEEL {
            override val colorResId: Int = R.color.steelGrey
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to EFFECTIVE,
                    ELECTRIC to NOT_VERY_EFFECTIVE,
                    FAIRY to SUPER_EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to NOT_VERY_EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to EFFECTIVE,
                    GROUND to EFFECTIVE,
                    ICE to SUPER_EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to SUPER_EFFECTIVE,
                    STEEL to NOT_VERY_EFFECTIVE,
                    WATER to NOT_VERY_EFFECTIVE
                )
            }
        },
        @Json(name = "water")
        WATER {
            override val colorResId: Int = R.color.waterBlue
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to EFFECTIVE,
                    DARK to EFFECTIVE,
                    DRAGON to NOT_VERY_EFFECTIVE,
                    ELECTRIC to EFFECTIVE,
                    FAIRY to EFFECTIVE,
                    FIGHTING to EFFECTIVE,
                    FIRE to SUPER_EFFECTIVE,
                    FLYING to EFFECTIVE,
                    GHOST to EFFECTIVE,
                    GRASS to NOT_VERY_EFFECTIVE,
                    GROUND to SUPER_EFFECTIVE,
                    ICE to EFFECTIVE,
                    NORMAL to EFFECTIVE,
                    POISON to EFFECTIVE,
                    PSYCHIC to EFFECTIVE,
                    ROCK to SUPER_EFFECTIVE,
                    STEEL to EFFECTIVE,
                    WATER to NOT_VERY_EFFECTIVE
                )
            }
        },
        NA {
            override val colorResId: Int = R.color.material_grey_800
            override val typeEffectivenessMap: Map<Type, Float> by lazy {
                mapOf(
                    BUG to NO_EFFECT,
                    DARK to NO_EFFECT,
                    DRAGON to NO_EFFECT,
                    ELECTRIC to NO_EFFECT,
                    FAIRY to NO_EFFECT,
                    FIGHTING to NO_EFFECT,
                    FIRE to NO_EFFECT,
                    FLYING to NO_EFFECT,
                    GHOST to NO_EFFECT,
                    GRASS to NO_EFFECT,
                    GROUND to NO_EFFECT,
                    ICE to NO_EFFECT,
                    NORMAL to NO_EFFECT,
                    POISON to NO_EFFECT,
                    PSYCHIC to NO_EFFECT,
                    ROCK to NO_EFFECT,
                    STEEL to NO_EFFECT,
                    WATER to NO_EFFECT
                )
            }
        };

        abstract val colorResId: Int
        abstract val typeEffectivenessMap: Map<Type, Float>

        companion object {
            const val SUPER_EFFECTIVE = 2.0f
            const val EFFECTIVE = 1.0f
            const val NOT_VERY_EFFECTIVE = 0.5f
            const val NO_EFFECT = 0.0f

            fun calculateWeaknesses(pokemonTypes: List<Type>): Map<Type, Float> =
                values()
                    .filterNot { it == NA }
                    .map { type ->
                        type to pokemonTypes.fold(1.0f) { acc, pokemonType ->
                            acc * type.typeEffectivenessMap.getValue(pokemonType)
                        }
                    }
                    .filter { it.second >= SUPER_EFFECTIVE }
                    .toMap()
                    .ifEmpty { mapOf(NA to NO_EFFECT) }

            fun calculateStrengths(pokemonTypes: List<Type>): Map<Type, Float> {
                return values()
                    .filterNot { it == NA }
                    .map { type ->
                        type to pokemonTypes.fold(1.0f) { acc, pokemonType ->
                            acc * pokemonType.typeEffectivenessMap.getValue(type)
                        }
                    }
                    .filter { it.second >= SUPER_EFFECTIVE }
                    .toMap()
                    .ifEmpty { mapOf(NA to NO_EFFECT) }
            }
        }
    }
}
