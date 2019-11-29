package com.naranjo.kristian.pokemonandroid.datastore

data class Pokemon(val name: String, val number: String, val types: List<Type>, val imageUrl: String)

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