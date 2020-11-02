package me.rooshi.podcastapp.feature.main.subscriptions

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.recyclerview.widget.RecyclerView
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.kotlin.withLatestFrom
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.podcastapp.common.base.MyViewModel
import me.rooshi.podcastapp.feature.main.episodeList.EpisodeItem

class SubscriptionsViewModel @ViewModelInject constructor(
    private val podcastRepository: PodcastRepository
) : MyViewModel<SubscriptionsView, SubscriptionsState>(SubscriptionsState()) {

    override fun bindView(view: SubscriptionsView) {
        super.bindView(view)

        view.onNewIntentIntent
                .switchMap {
                    Log.e("newintent", "feed called")
                    podcastRepository.getSubscriptionFeed(null)
                }
                .doOnNext { subscriptionListResult ->
                    val itemList = mutableListOf<SubscriptionEpisodeItem>()
                    for (e in subscriptionListResult.episodes) {
                        itemList.add(SubscriptionEpisodeItem(e))
                    }
                    newState { copy(subscriptionEpisodes = itemList, next = subscriptionListResult.next) }
                }
                .autoDispose(view.scope())
                .subscribe()

        view.bottomScrollReachedIntent
                .filter {event ->
                    !event.view.canScrollVertically(RecyclerView.FOCUS_DOWN)
                }
                .withLatestFrom(state)
                .switchMap {
                    Log.e("feed", "reached bottom scroll")
                    val state = it.second
                    podcastRepository.getSubscriptionFeed(state.next)
                }
                .withLatestFrom(state)
                .autoDispose(view.scope())
                .subscribe {
                    Log.e("bottom scroll", "add")
                    val itemList = it.second.subscriptionEpisodes
                    for (e in it.first.episodes) {
                        itemList.add(SubscriptionEpisodeItem(e))
                    }

                    newState { copy(subscriptionEpisodes = itemList, next = it.first.next) }
                }
    }


}