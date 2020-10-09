package me.rooshi.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast


interface PlayerRepository {

    val currentEpisode: Observable<Episode>

    fun loadPodcastInfo()

    //other fragments will probably call this repo to load podcast information
    //then this repo would have to send info to the playerfragment
    fun changeEpisode(episode: Episode)
}