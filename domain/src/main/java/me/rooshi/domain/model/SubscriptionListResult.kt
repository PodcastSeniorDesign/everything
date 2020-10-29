package me.rooshi.domain.model

data class SubscriptionListResult(
        val episodes: List<Episode> = listOf(),
        val next: HashMap<*,*>? = null
)