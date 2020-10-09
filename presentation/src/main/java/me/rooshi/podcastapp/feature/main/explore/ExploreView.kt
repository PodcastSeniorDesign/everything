package me.rooshi.podcastapp.feature.main.explore

import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.base.MyView

interface ExploreView : MyView<ExploreState> {
    val searchIntent: Observable<Unit>
}