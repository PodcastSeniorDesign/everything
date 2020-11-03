package me.rooshi.podcastapp.feature.main.social.addFriend

import me.rooshi.domain.model.Genre
import me.rooshi.podcastapp.R

data class AddFriendState(
        val users: List<AddFriendItem> = listOf(),
        val finished: Boolean = false
)