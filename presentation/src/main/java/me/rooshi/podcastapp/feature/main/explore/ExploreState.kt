package me.rooshi.podcastapp.feature.main.explore

import me.rooshi.domain.model.Episode
import me.rooshi.podcastapp.feature.main.explore.recommendation.RecommendItem

data class ExploreState(
        val recommendationData: List<RecommendItem> = ArrayList()
)