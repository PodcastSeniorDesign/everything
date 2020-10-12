package me.rooshi.domain.model

//could make it an open class if needed
//might have to be a data class for firebase
data class Podcast(
        val id: String = "",
        val name: String = "",
        val creator: String = "",
        val publishedDate: Long = 0, //type may change
        val url: String = "",
        val episodes: List<Episode>? = null,
        val subscribers: List<User>? = null

)