package me.rooshi.podcastapp.feature.main.subscriptions

import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

data class SubscriptionsState(
        val subscriptionEpisodes: List<SubscriptionEpisodeItem> = listOf(),
        val next: Long = 0
)