package me.rooshi.podcastapp.feature.main.podcastInfo

import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

data class PodcastInfoState(
        val podcast: Podcast? = null,
        val episodes: List<EpisodeItem> = listOf(),
        val nextCallInfo: Long? = null
)