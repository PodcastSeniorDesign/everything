package me.rooshi.domain.model

data class Episode(
        var id: String = "",
        var imageURL: String = "",
        var thumbnailURL: String = "",
        var title: String = "",
        var description: String = "",
        var likes: Int = 0,
        var audioURL: String = "",
        var lengthSeconds: Int = -1,
        var dateMilli: Long = -1,
        var podcast: Podcast = Podcast()
)