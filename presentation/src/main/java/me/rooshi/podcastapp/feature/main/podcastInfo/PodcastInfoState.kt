package me.rooshi.podcastapp.feature.main.podcastInfo

import me.rooshi.domain.model.Podcast

data class PodcastInfoState(
        val podcast: Podcast = Podcast()
)