package me.rooshi.podcastapp.feature.main.social.newPost

import me.rooshi.domain.model.Genre
import me.rooshi.podcastapp.R

data class NewPostState(
        val postText: String = "",
        val finished: Boolean = false
)