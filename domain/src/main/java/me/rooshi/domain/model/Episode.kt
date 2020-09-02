package me.rooshi.domain.model

class Episode(val id: String,
        val url: String,
        val length: Long)
// should the episode have a link to the parent?
//  probably but is that bad code?