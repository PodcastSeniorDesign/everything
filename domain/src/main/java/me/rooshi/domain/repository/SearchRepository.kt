package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.model.PodcastInfoResult

interface SearchRepository {
    fun searchPodcasts(query: String) : Observable<List<Podcast>>
    fun getListOfEpisodes(podcast: Podcast, next:Long) : Observable<PodcastInfoResult>
    fun getTopByGenre() : Observable<List<List<Episode>>>
    fun subscribePodcast(id: String) : Observable<String>
    fun unsubscribePodcast(id: String) : Observable<String>
    fun isSubscribed(id: String) : Observable<String>
}