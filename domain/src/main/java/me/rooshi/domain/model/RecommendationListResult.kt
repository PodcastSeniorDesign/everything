package me.rooshi.domain.model

data class RecommendationListResult(
        val recommendations: List<Episode> = listOf(),
        val next: Long = 0
)