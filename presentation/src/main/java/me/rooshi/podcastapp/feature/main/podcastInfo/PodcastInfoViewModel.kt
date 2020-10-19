package me.rooshi.podcastapp.feature.main.podcastInfo

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.RecyclerView
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.google.gson.Gson
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.repository.SearchRepository
import me.rooshi.podcastapp.common.base.MyViewModel
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem
import me.rooshi.podcastapp.feature.main.explore.search.SearchItem

class PodcastInfoViewModel @ViewModelInject constructor(
        private val gson: Gson,
        private val searchRepository: SearchRepository
) : MyViewModel<PodcastInfoView, PodcastInfoState>(PodcastInfoState()) {

    override fun bindView(view: PodcastInfoView) {
        super.bindView(view)

        view.onNewIntentIntent
                .switchMap {intent ->
                    val podcast = gson.fromJson<Podcast>(intent.getStringExtra("podcast"), Podcast::class.java)
                    newState { copy(podcast = podcast) }
                    searchRepository.getListOfEpisodes(podcast, 0)
                }
                .doOnNext {
                    infoResult ->
                    val itemList = mutableListOf<EpisodeItem>()
                    for (e in infoResult.episodeList) {
                        itemList.add(EpisodeItem(e))
                    }
                    newState { copy(episodes = itemList, nextCallInfo = infoResult.nextCallInfo) }
                }
                .withLatestFrom(state)
                .switchMap {
                    Log.e("id before check", it.second.podcast.id)
                    searchRepository.isSubscribed(it.second.podcast.id)
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    val podcast = it.second.podcast
                    Log.e("result", it.first)
                    if (it.first == "true") {
                        podcast.subscribed = true
                    }
                    newState { copy(podcast = podcast) }
                }

        view.bottomScrollReachedIntent
                .filter {event ->
                    !event.view.canScrollVertically(RecyclerView.FOCUS_DOWN)
                }
                .withLatestFrom(state)
                .switchMap {
                    Log.e("asdfsdf", "reached bottom scroll")
                    val state = it.second
                    searchRepository.getListOfEpisodes(state.podcast, state.nextCallInfo)
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    val itemList = it.second.episodes
                    for (e in it.first.episodeList) {
                        itemList.add(EpisodeItem(e))
                    }

                    newState { copy(episodes = itemList, nextCallInfo = it.first.nextCallInfo) }
                }

        view.subscribeIntent
                .withLatestFrom(state)
                .switchMap {
                    if (it.second.podcast.subscribed) {
                        searchRepository.unsubscribePodcast(it.second.podcast.id)
                    } else {
                        searchRepository.subscribePodcast(it.second.podcast.id)
                    }
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    val podcast = it.second.podcast
                    if (it.first == "unsubscribed") {
                        podcast.subscribed = false
                    } else if (it.first == "subscribed") {
                        podcast.subscribed = true
                    }
                    newState { copy(podcast = podcast) }
                    Log.e("sub finished", it.second.podcast.subscribed.toString())
                }

    }
}