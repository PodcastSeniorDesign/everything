package me.rooshi.podcastapp.feature.main.explore

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import io.reactivex.rxjava3.kotlin.plusAssign
import me.rooshi.domain.repository.SearchRepository
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel

class ExploreViewModel @ViewModelInject constructor(
    private val navigator: Navigator,
    private val searchRepository: SearchRepository
) : MyViewModel<ExploreView, ExploreState>(ExploreState()) {



    init {
        disposables += searchRepository.topPodcastsByGenre()
                .subscribe {
                    Log.e("asdf", "asdf")
                }
    }

    override fun bindView(view: ExploreView) {
        super.bindView(view)

        view.searchIntent
                .autoDispose(view.scope())
                .subscribe { navigator.startSearchActivity() }
    }
}