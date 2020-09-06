package me.rooshi.domain.model

//This class should represent a post and a comment
// If we're doing it like twitter, the parent post has no parent, but "comments" have a parent and
//  we just display them differently if needed. Comments should also be able to link to other clips etc.

//We don't need a like class, just a list of users who liked this post.
// A reference to a liked post should also be put in the user model so they can see what posts they have liked.

data class Post (val id: String,
            val userId: String,
            val date: Long,
            val bodyText: String,
            val likes: List<User>,
            val shares: List<User>,
            val comments: List<Post>,
            val clip: Clip?)