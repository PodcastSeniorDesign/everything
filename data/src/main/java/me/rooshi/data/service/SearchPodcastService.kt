package me.rooshi.data.service

import me.rooshi.domain.model.Podcast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchPodcastService {

    companion object {
        val baseUrl = "https://us-central1-podcast-app-32d03.cloudfunctions.net"
    }

    @GET("/podcasts-search")
    fun searchPodcasts(@Query("") key : String): Call<Podcast>

}