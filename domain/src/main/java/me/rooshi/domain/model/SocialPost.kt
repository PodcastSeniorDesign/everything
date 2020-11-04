package me.rooshi.domain.model

//This class should represent a post and a comment
// If we're doing it like twitter, the parent post has no parent, but "comments" have a parent and
//  we just display them differently if needed. Comments should also be able to link to other clips etc.

//We don't need a like class, just a list of users who liked this post.
// A reference to a liked post should also be put in the user model so they can see what posts they have liked.

data class SocialPost (
        var id: String = "",
        var userId: String = "",
        var date: Long = 0,
        var bodyText: String = "",
        var comments: List<User> = listOf(),
        var shares: List<User> = listOf(),
        var likes: List<User> = listOf(),
        var clip: Clip? = null
)