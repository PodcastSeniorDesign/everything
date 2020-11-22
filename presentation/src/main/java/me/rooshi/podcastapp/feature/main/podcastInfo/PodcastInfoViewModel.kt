package me.rooshi.podcastapp.feature.main.podcastInfo

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.recyclerview.widget.RecyclerView
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.google.gson.Gson
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.model.Podcast
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.podcastapp.common.base.MyViewModel
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

class PodcastInfoViewModel @ViewModelInject constructor(
        private val gson: Gson,
        private val podcastRepository: PodcastRepository
) : MyViewModel<PodcastInfoView, PodcastInfoState>(PodcastInfoState()) {

    override fun bindView(view: PodcastInfoView) {
        super.bindView(view)

        view.onNewIntentIntent
                .doOnNext {
                    view.startedLoading()
                }
                .switchMap {intent ->
                    val podcast = gson.fromJson<Podcast>(intent.getStringExtra("podcast"), Podcast::class.java)
                    newState { copy(podcast = podcast) }
                    podcastRepository.getListOfEpisodes(podcast, 0)
                }
                .doOnNext {
                    infoResult ->
                    val itemList = mutableListOf<EpisodeItem>()
                    for (e in infoResult.episodeList) {
                        itemList.add(EpisodeItem(e))
                    }
                    newState { copy(episodes = itemList, nextCallInfo = infoResult.next) }
                }
                .withLatestFrom(state)
                .switchMap {
                    Log.e("id before check", it.second.podcast.id)
                    podcastRepository.isSubscribed(it.second.podcast.id)
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
                    view.finishedLoading()
                }

        view.bottomScrollReachedIntent
                .filter {event ->
                    !event.view.canScrollVertically(RecyclerView.FOCUS_DOWN)
                }
                .doOnNext {
                    view.startedLoading()
                }
                .withLatestFrom(state)
                .switchMap {
                    Log.e("asdfsdf", "reached bottom scroll")
                    val state = it.second
                    podcastRepository.getListOfEpisodes(state.podcast, state.nextCallInfo)
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    val itemList = it.second.episodes
                    for (e in it.first.episodeList) {
                        itemList.add(EpisodeItem(e))
                    }

                    newState { copy(episodes = itemList, nextCallInfo = it.first.next) }
                    view.finishedLoading()
                }

        view.subscribeIntent
                .withLatestFrom(state)
                .switchMap {
                    if (it.second.podcast.subscribed) {
                        podcastRepository.unsubscribePodcast(it.second.podcast.id)
                    } else {
                        podcastRepository.subscribePodcast(it.second.podcast.id)
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