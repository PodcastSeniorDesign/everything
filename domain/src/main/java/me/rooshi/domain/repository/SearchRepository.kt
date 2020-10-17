package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.model.PodcastInfoResult

interface SearchRepository {
    fun searchPodcasts(query: String) : Observable<List<Podcast>>
    fun topPodcastsByGenre() : Observable<List<List<Podcast>>>
    fun getListOfEpisodes(podcast: Podcast, next:Long): Observable<PodcastInfoResult>
}