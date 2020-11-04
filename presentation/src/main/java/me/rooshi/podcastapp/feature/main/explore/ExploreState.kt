package me.rooshi.podcastapp.feature.main.explore

import me.rooshi.domain.model.GenreRowItem
import me.rooshi.podcastapp.feature.main.explore.recommendation.RecommendItem

data class ExploreState(
        val recommendationData: List<List<RecommendItem>> = listOf(),
        val topData: List<GenreRowItem> = listOf()
)