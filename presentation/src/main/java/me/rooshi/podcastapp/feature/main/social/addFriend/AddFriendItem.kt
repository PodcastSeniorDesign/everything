package me.rooshi.podcastapp.feature.main.social.addFriend

import me.rooshi.domain.model.Genre
import me.rooshi.domain.model.User

data class AddFriendItem(
        val user: User,
        var clicked: Boolean = false
)