package me.rooshi.podcastapp.feature.main.social.newComment

import me.rooshi.domain.model.SocialPost

data class NewCommentState(
        val post: SocialPost = SocialPost(),
        val commentText: String = "",
        val finished: Boolean = false
)