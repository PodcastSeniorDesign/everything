package me.rooshi.podcastapp.feature.main.explore

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.domain.repository.PlayerRepository
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel
import me.rooshi.podcastapp.feature.main.explore.recommendation.RecommendItem
import me.rooshi.podcastapp.feature.main.player.PlayerController

class ExploreViewModel @ViewModelInject constructor(
    private val navigator: Navigator,
    private val podcastRepository: PodcastRepository,
    private val playerRepository: PlayerRepository
) : MyViewModel<ExploreView, ExploreState>(ExploreState()) {

    override fun bindView(view: ExploreView) {
        super.bindView(view)

        view.searchIntent
                .autoDispose(view.scope())
                .subscribe {
                    Log.e("search bar clicked", "now lol")
                    navigator.startSearchActivity() }

        view.onNewIntentIntent
                .switchMap {
                    Log.e("onnewintentintent", "top genre called")
                    podcastRepository.getTopByGenre()
                }
                .autoDispose(view.scope())
                .subscribe {
                    newState { copy(topData = it) }
                }

        view.onNewIntentIntent
                .switchMap {
                    Log.e("onnewintentintent", "recommend called")
                    podcastRepository.getRecommendedEpisodes()
                }
                .autoDispose(view.scope())
                .subscribe {
                    val list = mutableListOf<RecommendItem>()
                    for (i in it) {
                        list.add(RecommendItem(podcast = i))
                    }
                    newState { copy(recommendationData = list) }
                }

        view.topClickIntent
                .autoDispose(view.scope())
                .subscribe {
                    playerRepository.changeEpisode(it)
                }
    }
}