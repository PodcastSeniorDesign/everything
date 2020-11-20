package me.rooshi.podcastapp.feature.main.explore

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.domain.model.Episode
import me.rooshi.domain.model.Podcast
import me.rooshi.podcastapp.common.base.MyView

interface ExploreView : MyView<ExploreState> {
    val searchIntent: Observable<Unit>
    val onNewIntentIntent: Subject<Unit>
    val topClickIntent: Subject<Podcast>

    fun startedLoading()
    fun finishedLoading()
}