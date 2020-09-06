package me.rooshi.domain.model

//could make it an open class if needed
//might have to be a data class for firebase
data class Podcast(val id: String,
              val name: String,
              val creator: String,
              val publishedDate: Long, //type may change
              val url: String,
              val episodes: List<Episode>,
              val subscribers: List<User>

)