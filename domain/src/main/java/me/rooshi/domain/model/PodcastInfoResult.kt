package me.rooshi.domain.model

data class PodcastInfoResult(
        val episodeList: List<Episode> = listOf(),
        val nextCallInfo: Long = 0
)