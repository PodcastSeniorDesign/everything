package me.rooshi.domain.model

//could make it an open class if needed
//might have to be a data class for firebase
data class Podcast(
        var imageURL: String = "",
        var thumbnailURL: String = "",
        var description: String = "",
        var websiteURL: String = "",
        var title: String = "",
        var publisher: String = "",
        var id: String = "",
        var totalEpisodes: Int = 0
)