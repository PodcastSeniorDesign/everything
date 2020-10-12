package me.rooshi.podcastapp.feature.main.explore

import androidx.hilt.lifecycle.ViewModelInject
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyViewModel

class ExploreViewModel @ViewModelInject constructor(
    private val navigator: Navigator
) : MyViewModel<ExploreView, ExploreState>(ExploreState()) {

    override fun bindView(view: ExploreView) {
        super.bindView(view)

        view.searchIntent
                .autoDispose(view.scope())
                .subscribe { navigator.startSearchActivity() }
    }
}