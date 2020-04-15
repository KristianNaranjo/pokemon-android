package com.naranjo.kristian.pokemonandroid.ui.widgets

import android.content.Context
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class CustomFlexboxLayoutMananger @JvmOverloads constructor(
    context: Context,
    @FlexDirection flexDirection: Int = FlexDirection.ROW,
    @FlexWrap flexWrap: Int = FlexWrap.WRAP
) : FlexboxLayoutManager(context, flexDirection, flexWrap) {

    override fun canScrollVertically(): Boolean = false
}