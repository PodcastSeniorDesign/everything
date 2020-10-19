package me.rooshi.podcastapp.feature.main.explore

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface ExploreView : MyView<ExploreState> {
    val searchIntent: Observable<Unit>
    val onNewIntentIntent: Subject<Unit>
}