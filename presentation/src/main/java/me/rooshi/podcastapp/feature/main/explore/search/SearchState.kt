package me.rooshi.podcastapp.feature.main.explore.search

data class SearchState(
        val searchTerm: String = "",
        val results: List<SearchItem> = ArrayList()
)