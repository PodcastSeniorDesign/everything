package me.rooshi.podcastapp.feature.favoriteGenre

import me.rooshi.domain.model.Genre

data class FavoriteGenreItem(
        val genre: Genre,
        var clicked: Boolean = false
)