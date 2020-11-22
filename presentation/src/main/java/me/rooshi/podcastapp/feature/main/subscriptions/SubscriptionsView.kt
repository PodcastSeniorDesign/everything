package me.rooshi.podcastapp.feature.main.subscriptions

import com.jakewharton.rxbinding4.view.ViewScrollChangeEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.common.base.MyView

interface SubscriptionsView : MyView<SubscriptionsState> {
    val onNewIntentIntent: Subject<Unit>
    val bottomScrollReachedIntent: Observable<ViewScrollChangeEvent>

    fun startedLoading()
    fun finishedLoading()
}