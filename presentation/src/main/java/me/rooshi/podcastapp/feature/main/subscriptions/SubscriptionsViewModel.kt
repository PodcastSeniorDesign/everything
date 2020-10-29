package me.rooshi.podcastapp.feature.main.subscriptions

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.podcastapp.common.base.MyViewModel

class SubscriptionsViewModel @ViewModelInject constructor(
    private val podcastRepository: PodcastRepository
) : MyViewModel<SubscriptionsView, SubscriptionsState>(SubscriptionsState()) {

    override fun bindView(view: SubscriptionsView) {
        super.bindView(view)

        view.onNewIntentIntent
                .switchMap {
                    Log.e("newintent", "feed called")
                    podcastRepository.getSubscriptionFeed()
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
    }
}