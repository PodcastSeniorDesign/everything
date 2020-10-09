package me.rooshi.podcastapp.feature.main.player

import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast

data class PlayerState(
        val playing: Boolean = false,
        val episode: Episode? = null,
        val timer: Int = 0,
        val episodeLength: Int = 0,
        val episodeLoaded: Boolean = false,
        val seeking: Boolean = false
)