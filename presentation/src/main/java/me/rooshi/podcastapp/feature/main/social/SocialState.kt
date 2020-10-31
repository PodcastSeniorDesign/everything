package me.rooshi.podcastapp.feature.main.social

data class SocialState(
        val posts: MutableList<SocialPostItem> = mutableListOf()
)