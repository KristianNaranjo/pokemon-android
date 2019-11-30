package com.naranjo.kristian.pokemonandroid.datastore

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(val name: String, val number: String, val types: List<Type>, val imageUrl: String): Parcelable

enum class Type {
    WATER,
    FIRE,
    GRASS,
    ICE,
    FIGHTING,
    DRAGON,
    FLYING,
    ELECTRIC,
    NORMAL,
    FAIRY
}