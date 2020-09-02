package me.rooshi.domain.model

//could make it an open class if needed
//might have to be a data class for firebase
class Podcast(val id: String,
              val name: String,
              val url: String,
              val episodes: List<Episode>

)