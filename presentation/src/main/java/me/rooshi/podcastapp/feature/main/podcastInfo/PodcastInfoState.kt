package me.rooshi.podcastapp.feature.main.podcastInfo

import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

data class PodcastInfoState(
        val podcast: Podcast = Podcast(),
        val episodes: MutableList<EpisodeItem> = mutableListOf(),
        val nextCallInfo: Long = 0
)