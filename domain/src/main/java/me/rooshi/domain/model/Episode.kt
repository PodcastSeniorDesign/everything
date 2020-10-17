package me.rooshi.domain.model

data class Episode(
        var id: String = "",
        var imageURL: String = "",
        var thumbnailURL: String = "",
        var title: String = "",
        var description: String = "",
        var likes: Int = 0,
        var audioURL: String = "",
        var length: Int = 0,
        var dateMilli: Long = 0,
        var podcast: Podcast = Podcast()
)