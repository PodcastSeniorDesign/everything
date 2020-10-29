package me.rooshi.podcastapp.feature.main.explore

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.domain.repository.PodcastRepository
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel

class ExploreViewModel @ViewModelInject constructor(
    private val navigator: Navigator,
    private val podcastRepository: PodcastRepository
) : MyViewModel<ExploreView, ExploreState>(ExploreState()) {

    override fun bindView(view: ExploreView) {
        super.bindView(view)

        view.searchIntent
                .autoDispose(view.scope())
                .subscribe { navigator.startSearchActivity() }

        view.onNewIntentIntent
                .switchMap {
                    Log.e("onnewintentintent", "top genre called")
                    podcastRepository.getTopByGenre()
                }
                .autoDispose(view.scope())
                .subscribe()

        view.onNewIntentIntent
                .switchMap {
                    Log.e("onnewintentintent", "recommend called")
                    podcastRepository.getRecommendedEpisodes()
                }
                .autoDispose(view.scope())
                .subscribe()
    }
}