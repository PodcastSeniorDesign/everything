package me.rooshi.podcastapp.feature.main.podcastInfo

import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast

data class PodcastInfoState(
        val podcast: Podcast? = null,
        val episodes: List<Episode>? = null
)