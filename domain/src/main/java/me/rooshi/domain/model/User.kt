package me.rooshi.domain.model

//MODELS SHOULD NOT HAVE ANY CALCULATIONS DONE WITH THEM
//IF YOU FIND YOURSELF NEEDING TO GET SOME DATA THATS NOT IN THIS CLASS, THEN YOU NEED TO CREATE
//      A METHOD IN A REPOSITORY OR CREATE A NEW REPOSITORY
data class User(
        val id: String,
        val following: List<User>,
        val followers: List<User>,
        val posts: List<Post>,
        val subscriptions: List<Podcast>,
        val likedEpisodes: List<Episode>
)