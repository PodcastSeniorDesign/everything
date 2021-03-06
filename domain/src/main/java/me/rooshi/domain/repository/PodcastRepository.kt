package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.*

interface PodcastRepository {
    fun searchPodcasts(query: String) : Observable<List<Podcast>>
    fun getListOfEpisodes(podcast: Podcast, next:Long) : Observable<PodcastInfoResult>
    fun getTopByGenre() : Observable<List<GenreRowItem>>
    fun subscribePodcast(id: String) : Observable<String>
    fun unsubscribePodcast(id: String) : Observable<String>
    fun isSubscribed(id: String) : Observable<String>
    fun getSubscriptionFeed(next: HashMap<*,*>?) : Observable<SubscriptionListResult>
    fun getRecommendedEpisodes(): Observable<List<Podcast>>
}