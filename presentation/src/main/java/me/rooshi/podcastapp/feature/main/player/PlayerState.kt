package me.rooshi.podcastapp.feature.main.player

data class PlayerState(
        val playing: Boolean = false,
        val episodeName: String = "",
        val podcastName: String = "",
        val episodeLength: Long = 0,
        val episodeDate: String = "",
        val episodeLoaded: Boolean = false
)