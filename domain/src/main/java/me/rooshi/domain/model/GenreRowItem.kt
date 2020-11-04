package me.rooshi.domain.model

import me.rooshi.domain.model.Episode

data class GenreRowItem(
        //the type of this may change
        var genre: String = "",
        var episodes: List<Episode> = listOf()
)