package me.rooshi.domain.model

data class Episode(
        val id: String = "",
        val name: String,
        val likes: Int,
        val url: String,
        val length: Long,
        val date: String,
        val podcast: Podcast)
// should the episode have a link to the parent?
//  probably but is that bad code?