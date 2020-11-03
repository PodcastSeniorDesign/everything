package me.rooshi.podcastapp.feature.favoritegenre

import me.rooshi.domain.model.Genre

data class FavoriteGenreItem(
        val genre: Genre,
        var clicked: Boolean = false
)