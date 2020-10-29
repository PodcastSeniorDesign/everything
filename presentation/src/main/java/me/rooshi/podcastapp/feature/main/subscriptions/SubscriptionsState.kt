package me.rooshi.podcastapp.feature.main.subscriptions

import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

data class SubscriptionsState(
        val subscriptionEpisodes: MutableList<SubscriptionEpisodeItem> = mutableListOf(),
        val next: HashMap<*,*>? = null
)