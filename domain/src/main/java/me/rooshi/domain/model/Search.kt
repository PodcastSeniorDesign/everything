package me.rooshi.domain.model

data class Search(
        val searchTerm: String,
        val results: List<Podcast>
)