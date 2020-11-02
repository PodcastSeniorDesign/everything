package me.rooshi.domain.model

data class PodcastInfoResult(
        val episodeList: List<Episode> = listOf(),
        val next: Long = 0
)