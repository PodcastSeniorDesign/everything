package me.rooshi.domain.model

data class Episode(
        val id: String = "",
        val name: String = "",
        val likes: Int = 0,
        val url: String = "",
        val length: Long = 0,
        val date: String = "",
        val podcast: Podcast? = null
)
// should the episode have a link to the parent?
//  probably but is that bad code?