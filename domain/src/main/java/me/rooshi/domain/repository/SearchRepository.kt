package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Podcast

interface SearchRepository {
    fun searchPodcasts(query: String) : Observable<List<Podcast>>
    fun topPodcastsByGenre() : Observable<List<List<Podcast>>>
}